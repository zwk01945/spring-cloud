package com.cloud.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisUtils
 * Description: redis工具类
 * date: 2020/7/22 15:14
 * @author zwk
 */
@Component
public class RedisUtils {


    private static StringRedisTemplate redisTemplate;

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired(required = false)
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
        RedisUtils.redisTemplate.setEnableTransactionSupport(true);
    }

    /**
     * 以Map数据结构形式存入到redis中
     * 没有过期时间
     * @param params
     */
    public static void redisMap( Map<String,String> params){
        params.forEach((key, value) -> redisTemplate.opsForValue().set(key, value));
    }

    /**
     * 删除一组key
     * @param params
     */
    public static boolean redisMapRemove(List<String> params){
        boolean done = false;
        try {
            params.forEach((key) -> redisTemplate.delete(key));
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return done;
    }


    /**
     * 以String存入到redis中
     * 并给定相同的过期时间
     * @param params
     * @param time
     * @param timeUnit
     */
    public static void redisMapExpire(Map<String,String> params, Long time, TimeUnit timeUnit){
        params.forEach((key, value) -> {
            redisTemplate.opsForValue().set(key, value, time,timeUnit);
        });
    }

    /**
     * 获取到当前Map下所有key的过期时间
     * @param params
     * @return
     */
    public static Map<String,Long> getExpireByMap(Map<String,String> params) {
        Map<String,Long> result = new HashMap<>();
        params.forEach((key, value) -> {
           final Long expire = redisTemplate.getExpire(key);
           result.put(key,expire);
        });
        return result;
    }


    public static Object getValue(String key) {
      return  redisTemplate.opsForValue().get(key);
    }


}
