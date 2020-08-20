package com.cloud.springcloudslaveservice.controller;

import com.cloud.bean.ResultObject;
import com.cloud.bean.ohters.IcpCode;
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

    @RequestMapping("/i")
    public ResultObject insert () {
        try {
            IcpCode icpCode = new IcpCode();
            icpCode.setId(200);
            icpCode.setName("test");
            icpCodeService.insert(icpCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultObject(-1,"fail",null);
        }
        return new ResultObject(200,"success",null);
    }

}
