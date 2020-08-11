package com.cloud.aop.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: CacheRedis
 * Description: 缓存注解
 * date: 2020/7/24 13:26
 * @author Mr.zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface CacheRedis {
    Class<?> returnType() default Object.class;
    String[] value() default {};
    TimeUnit unit() default TimeUnit.SECONDS;
    long time() default -1L;
}
