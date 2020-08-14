package com.cloud.springcloudslaveservice.controller;

import com.cloud.springcloudslaveservice.service.IcpCodeService;
import com.cloud.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisController
 * Description:
 * date: 2020/7/22 16:41
 *
 * @author zyl
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    IcpCodeService icpCodeService;

    @RequestMapping("/all")
    public Object selectAll () {
        return icpCodeService.selectAll();
    }

    @RequestMapping("/q")
    public Object putRedis () {
        Map<String,String> each = new HashMap<>();
        each.put("测试1","success");
        each.put("测试2","success");
        each.put("测试3","success");
        RedisUtils.redisMap(each);
        return RedisUtils.getExpireByMap(each);
    }

    @RequestMapping("/w")
    public Object putRedisExpire () {
        Map<String,String> each = new HashMap<>();
        each.put("测试1","success");
        each.put("测试2","success");
        each.put("测试3","success");
        RedisUtils.redisMapExpire(each,20L, TimeUnit.SECONDS);
        return RedisUtils.getExpireByMap(each);
    }

}
