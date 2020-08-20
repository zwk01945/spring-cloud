package com.cloud.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : DsConfig.java                          *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   多数据源配置                                               *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
public class DsConfig {

    private static final Logger log = LoggerFactory.getLogger(DsConfig.class);

//
//    private MybatisPlusProperties properties;
//
//    private ResourceLoader resourceLoader = new DefaultResourceLoader();
//
//
//    private Interceptor[] interceptors;
//
//    private DatabaseIdProvider databaseIdProvider;


//    @Autowired
//    public void setProperties(MybatisPlusProperties properties) {
//        this.properties = properties;
//    }
//    @Autowired
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//    @Autowired(required = false)
//    public void setInterceptors(Interceptor[] interceptors) {
//        this.interceptors = interceptors;
//    }
//    @Autowired(required = false)
//    public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
//        this.databaseIdProvider = databaseIdProvider;
//    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }



    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean =
                new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),"/druid/*");

        //后台需要有人登录，进行配置
        //bean.addUrlMappings(); 这个可以添加映射，我们在构造里已经写了
        //设置一些初始化参数
        Map<String,String> initParas = new HashMap<String,String>();
        initParas.put("loginUsername","admin");//它这个账户密码是固定的
        initParas.put("loginPassword","123456");
        //允许谁能防伪
        initParas.put("allow","");//这个值为空或没有就允许所有人访问，ip白名单
        //initParas.put("allow","localhost");//只允许本机访问，多个ip用逗号,隔开
        //initParas.put("deny","");//ip黑名单，拒绝谁访问 deny和allow同时存在优先deny
        initParas.put("resetEnable","false");//禁用HTML页面的Reset按钮
        bean.setInitParameters(initParas);
        return bean;
    }

    //再配置一个过滤器，Servlet按上面的方式注册Filter也只能这样
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        //可以设置也可以获取,设置一个阿里巴巴的过滤器
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        //可以过滤和排除哪些东西
        Map<String,String> initParams = new HashMap<String,String>();
        //把不需要监控的过滤掉,这些不进行统计
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        return bean;
    }
}
