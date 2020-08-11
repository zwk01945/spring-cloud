package com.cloud.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : GlobalException.java                   *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   全局WEB拦截器/cors等网站拦截器扩展添加                         *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:favicon.ico");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/");
    }

    /**
     * 全局拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加字符拦截器
        registry.addInterceptor(new DefenseInterceptor()).addPathPatterns("/**");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }

    /**
     * 全局cors跨域解决
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("Content-Type","accept","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
                .allowedMethods("GET","POST","PUT","OPTIONS")
                .exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials")
                .maxAge(3600);
    }

}
