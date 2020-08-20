package com.cloud.springcloudmasterservice.controller;

import com.cloud.bean.ohters.CoustomerZt;
import com.cloud.controller.BaseController;
import com.cloud.springcloudmasterservice.service.impl.ICoustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwk
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/quartz")
public class CoustomerZtController extends BaseController {

    @Autowired
    ICoustomerServiceImpl coustomerService;

    @RequestMapping("/q")
    public Object hello () {
        return coustomerService.selectAll();
    }

    @RequestMapping("/o")
    public Object helloOne (Integer id) {
        if (id == null || id == 0) throw new RuntimeException("参数为空");
        Map<String,Object> params = new HashMap<>();
        params.put("ID",id);
        return coustomerService.selectOne(params);
    }

    @RequestMapping("/m")
    public Object helloMore () {
        return coustomerService.selectJoin();
    }
    @RequestMapping("/p")
    public Object helloMore (Integer start,Integer size) {
        return coustomerService.selectByPage(start,size);
    }

    @RequestMapping("/i")
    public Object insert() {
        CoustomerZt coustomerZt = new CoustomerZt();
        coustomerZt.setAge(200);
        coustomerZt.setId(200);
        coustomerZt.setJob("test");
        coustomerZt.setName("test");
        coustomerZt.setLocalAddress("address");
        coustomerZt.setProvinceFlag(200);
        coustomerService.insert(coustomerZt);
        return coustomerZt;
    }


}
