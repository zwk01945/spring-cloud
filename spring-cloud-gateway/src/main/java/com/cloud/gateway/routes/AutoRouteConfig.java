package com.cloud.gateway.routes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : AutoRouteConfig.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/24 16:35                       *
 *                                                            *
 *         Last Update : 2020/8/24 16:35                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
@RefreshScope
public class AutoRouteConfig implements ApplicationEventPublisherAware, CommandLineRunner, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(AutoRouteConfig.class);

    @Value("${nacos.serveraddr:#{null}}")
    private String SERVER_ADDR;
    @Value("${nacos.dataid:#{null}}")
    private String DATA_ID;
    @Value("${nacos.group:#{null}}")
    private String GROUP;
//    @Value("${nacos.namespace:#{null}}")
//    private String NAMESPACE;

    public RouteDefinitionWriter routeDefinitionWriter;

    public ApplicationEventPublisher applicationEventPublisher;

    public ConfigService configService;
    @Autowired
    public void setRouteDefinitionWriter(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("网关启动从nacos拉取网关配置信息.....");
        log.info("data_id,group,timeoutMs:{},{},{}",DATA_ID,GROUP,5000);
        String config = configService.getConfig(DATA_ID, GROUP, 5000);
        System.out.println(config);
        boolean nacosConfig = StringUtils.isEmpty(config);
        if (nacosConfig) {
            log.info("未拉取到服务器配置信息,或者服务器未配置.....");
        } else {
            boolean refreshGatewayRoute = JSONObject.parseObject(config).getBoolean("refreshGatewayRoute");
            if (refreshGatewayRoute) {
                List<RouteEntity> list = JSON.parseArray(JSONObject.parseObject(config).getString("routeList")).toJavaList(RouteEntity.class);
                list.forEach(route ->  update(assembleRouteDefinition(route)));
            } else {
                log.info("服务器未配置路由列表.....");
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("data_id,group:{},{}",DATA_ID,GROUP);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
        configService = NacosFactory.createConfigService(properties);
    }

    @Bean
    public void refreshRoutes() throws NacosException {
        log.info("初始化添加nacos listener.....");
        log.info("data_id,group:{},{}",DATA_ID,GROUP);
        configService.addListener(DATA_ID, GROUP, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                boolean refreshGatewayRoute = JSONObject.parseObject(configInfo).getBoolean("refreshGatewayRoute");
                if (refreshGatewayRoute) {
                    List<RouteEntity> list = JSON.parseArray(JSONObject.parseObject(configInfo).getString("routeList")).toJavaList(RouteEntity.class);
                    list.forEach(route ->  update(assembleRouteDefinition(route)));
                } else {
                    log.info("路由未发生变更");
                }
            }
        });

    }

    /**
     * 路由更新
     * @param routeDefinition
     * @return void
     * @author
     * @since 2020/3/15
     */
    public void update(RouteDefinition routeDefinition){
        try {
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
            log.info("路由移除成功");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        try {
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("路由更新成功");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public RouteDefinition assembleRouteDefinition(RouteEntity routeEntity) {
        RouteDefinition definition = new RouteDefinition();
        //ID
        definition.setId(routeEntity.getId());
        // Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();

        routeEntity.getPredicates().forEach(predicateEntity -> {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setArgs(predicateEntity.getArgs());
            predicateDefinition.setName(predicateEntity.getName());
            pdList.add(predicateDefinition);
        });
        definition.setPredicates(pdList);
        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();

        routeEntity.getFilters().forEach(filterEntity -> {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setArgs(filterEntity.getArgs());
            filterDefinition.setName(filterEntity.getName());
            fdList.add(filterDefinition);
        });
        definition.setFilters(fdList);
        URI uri = UriComponentsBuilder.fromUriString(routeEntity.getUri()).build().toUri();
        definition.setUri(uri);
        return definition;
    }
}
