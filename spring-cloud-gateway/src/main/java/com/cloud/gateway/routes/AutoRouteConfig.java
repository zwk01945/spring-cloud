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
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud                               *
 *                                                            *
 *         File Name : AutoRouteConfig.java                   *
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
public class AutoRouteConfig implements ApplicationEventPublisherAware, CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(AutoRouteConfig.class);

    @Value("${nacos.serveraddr:#{null}}")
    private String SERVER_ADDR;
    @Value("${nacos.dataid:#{null}}")
    private String DATA_ID;
    @Value("${nacos.group:#{null}}")
    private String GROUP;
//    @Value("${nacos.namespace:#{null}}")
//    private String NAMESPACE;

    private String SERVER_ADDR_OLD;
    private String DATA_ID_OLD;
    private String GROUP_OLD;

    public String getSERVER_ADDR_OLD() {
        return SERVER_ADDR_OLD;
    }

    public void setSERVER_ADDR_OLD(String SERVER_ADDR_OLD) {
        this.SERVER_ADDR_OLD = SERVER_ADDR_OLD;
    }

    public String getDATA_ID_OLD() {
        return DATA_ID_OLD;
    }

    public void setDATA_ID_OLD(String DATA_ID_OLD) {
        this.DATA_ID_OLD = DATA_ID_OLD;
    }

    public String getGROUP_OLD() {
        return GROUP_OLD;
    }

    public void setGROUP_OLD(String GROUP_OLD) {
        this.GROUP_OLD = GROUP_OLD;
    }

    public RouteDefinitionWriter routeDefinitionWriter;

    public ApplicationEventPublisher applicationEventPublisher;

    public final ThreadLocal<ConfigService> configService = new ThreadLocal<>();

    @Autowired
    public void setRouteDefinitionWriter(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public void envListener() {
        log.info("监听到工程yaml配置动态变化");
            //需要重新绑定监听特殊文件
            try {
                log.info("data_id,group:{},{}",DATA_ID,GROUP);
                setDATA_ID_OLD(DATA_ID);
                setGROUP_OLD(GROUP);
                setSERVER_ADDR_OLD(SERVER_ADDR);
                log.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);
                Properties properties = new Properties();
                properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
                ConfigService service = NacosFactory.createConfigService(properties);
                creatListener(service);
            } catch (NacosException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("网关启动创建网关配置信息配置类configService.....");
        log.info("data_id,group:{},{}",DATA_ID,GROUP);
        setDATA_ID_OLD(DATA_ID);
        setGROUP_OLD(GROUP);
        setSERVER_ADDR_OLD(SERVER_ADDR);
        log.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
        ConfigService service = NacosFactory.createConfigService(properties);
        //服务启动创建监听
        creatListener(service);
        //初始化拉取数据
        buildFromNacos();

    }

    private void buildFromNacos() throws NacosException {
        log.info("从nacos拉取网关配置信息.....");
        log.info("data_id,group,timeoutMs:{},{},{}",DATA_ID,GROUP,5000);
        String config = configService.get().getConfig(DATA_ID, GROUP, 5000);
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


    public void creatListener(ConfigService service) throws NacosException {
        log.info("初始化添加nacos listener.....");
        log.info("data_id,group:{},{}",DATA_ID,GROUP);
        service.addListener(DATA_ID, GROUP, new Listener() {
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
        configService.set(service);
    }

//    @Bean
//    public void refreshRoutes() throws NacosException {
//        log.info("初始化添加nacos listener.....");
//        log.info("data_id,group:{},{}",DATA_ID,GROUP);
//        configService.addListener(DATA_ID, GROUP, new Listener() {
//            @Override
//            public Executor getExecutor() {
//                return null;
//            }
//
//            @Override
//            public void receiveConfigInfo(String configInfo) {
//                boolean refreshGatewayRoute = JSONObject.parseObject(configInfo).getBoolean("refreshGatewayRoute");
//                if (refreshGatewayRoute) {
//                    List<RouteEntity> list = JSON.parseArray(JSONObject.parseObject(configInfo).getString("routeList")).toJavaList(RouteEntity.class);
//                    list.forEach(route ->  update(assembleRouteDefinition(route)));
//                } else {
//                    log.info("路由未发生变更");
//                }
//            }
//        });
//
//    }

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
