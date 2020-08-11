package com.cloud.aop;

import com.alibaba.fastjson.JSON;
import com.cloud.aop.annotation.CacheRedis;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : RedisAspect.java                       *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   redis切面类，在被@CacheRedis标注的方法都会进入到该类下的方法     *
 *   只可以标注在方法上，可以设置超大数组key和过期时间以及单位以及      *
 *   存放的数据类型                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Aspect
@Component
@Order(1)
public class RedisAspect implements InitializingBean {

    public static Logger logger = LoggerFactory.getLogger(RedisAspect.class);

    StringRedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    private  ValueOperations<String, String> operations = null;
    private  HashOperations<String, Object, Object> hashOperations = null;
    private  ListOperations<String, String> listOperations = null;
    private  SetOperations<String, String> setOperations = null;
    private  ZSetOperations<String, String> zSetOperations = null;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    @Override
    public void afterPropertiesSet() throws Exception {
            this.operations = redisTemplate.opsForValue();
            this.hashOperations=redisTemplate.opsForHash();
            this.listOperations = redisTemplate.opsForList();
            this.setOperations = redisTemplate.opsForSet();
            this.zSetOperations = redisTemplate.opsForZSet();

        logger.info("初始化切面参数:{},{},{},{},{}",operations,hashOperations,listOperations,setOperations,zSetOperations);
    }


    @Around("@within(com.cloud.aop.annotation.CacheRedis) || @annotation(com.cloud.aop.annotation.CacheRedis)")
    public Object beforeSwitchCs(ProceedingJoinPoint point) {
        logger.info("进去redis aspect切面");
        Object o = null;
        try {
            //获取需要访问的类的Class
            Class<?> clazz = point.getTarget().getClass();
            //获取需要访问的方法名
            String methodName = point.getSignature().getName();
            //获取参数
            Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = clazz.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(CacheRedis.class)) {
                CacheRedis cr = method.getAnnotation(CacheRedis.class);
                Class<?> returnType = method.getReturnType();
                o = setCache(cr.unit(),cr.time(),point,returnType,cr.value());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
       return o;
    }

    private Object setCache(TimeUnit unit,long time,ProceedingJoinPoint point,Class<?> returnType, String... cacheName) throws Throwable {
        Object result = null;
            try{
                readLock.lock();
                for (String s : cacheName) {
                    result = operations.get(s);
                    if (result != null) {
                        logger.info("从缓存库读取数据,key为:{}",s);
                        String redisCache = (String) result;
                        result = JSON.parseObject(redisCache,returnType);
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                readLock.unlock();
            }
            try{
                writeLock.lock();
                readLock.lock();
                if (result == null) {
                    logger.info("从数据库读取数据");
                    result = point.proceed();
                    for (String s:cacheName) {
                        operations.setIfAbsent(s, JSON.toJSONString(result),time,unit);
                    }
                }
            }finally {
                writeLock.unlock();
            }
        return result;
    }



//    @After("@within(com.example.common.aop.annotation.CacheRedis) || @annotation(com.example.common.aop.annotation.CacheRedis)")
//    public void afterSwitchDS (JoinPoint point) {
//        DataSourceContextHolder.clearDbType();
//        logger.info("数据源移除成功");
//    }

}
