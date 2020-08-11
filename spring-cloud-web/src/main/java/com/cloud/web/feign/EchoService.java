package com.cloud.web.feign;

import com.aliyuncs.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : EchoService.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/10 16:37                       *
 *                                                            *
 *         Last Update : 2020/8/10 16:37                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   阿里云短信推送服务调用                                       *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@FeignClient(name = "spring-cloud-msgpush")
public interface EchoService {

    /**
     * 普通服务
     * @param str
     * @return
     */
    @GetMapping(value = "/msg/echo/{str}")
    String echo(@PathVariable("str") String str);


    /**
     * 短线服务
     * @param phone
     * @param data
     * @param code
     * @return
     */
    @PostMapping(value = "/msg/send")
    CommonResponse send(@RequestParam("phone") String phone,@RequestParam("data") String data,@RequestParam("code") String code);

    @PostMapping(value = "/msg/query")
    CommonResponse querySendDetails(@RequestParam("phone")String phone,@RequestParam("date") String date,
                                    @RequestParam("currentPage")String currentPage,
                                    @RequestParam("pageSize")String pageSize);



}
