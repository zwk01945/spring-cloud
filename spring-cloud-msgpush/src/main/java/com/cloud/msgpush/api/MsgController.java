package com.cloud.msgpush.api;

import com.cloud.bean.msg.QueryParam;
import com.aliyuncs.CommonResponse;
import com.cloud.msgpush.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : MsgController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/10 15:12                       *
 *                                                            *
 *         Last Update : 2020/8/10 15:12                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   推送服务API接口                                            *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
@RequestMapping("/msg")
public class MsgController {

    ApiService apiService;

    @Autowired
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(value = "/refresh")
    public void refresh(){
        System.out.println("success");
    }

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return string;
    }

    @PostMapping(value = "/send")
    public CommonResponse send(String phone, String data, String code) {
        QueryParam param = new QueryParam();
        Map<String,Object> template = new HashMap<>();
        template.put(code,data);
        param.setPhoneNumber(phone);
        param.setTemplateParam(template);
        return apiService.send(param);
    }

    @PostMapping(value = "/query")
    public CommonResponse querySendDetails(String phone, String date, String currentPage,String pageSize) {
        QueryParam param = new QueryParam();
        param.setSendDate(date);
        param.setPhoneNumber(phone);
        param.setCurrentPage(currentPage);
        param.setPageSize(pageSize);
        return apiService.querySendDetails(param);
    }
}
