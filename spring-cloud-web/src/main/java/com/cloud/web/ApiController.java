package com.cloud.web;

import com.cloud.web.feign.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : ApiController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/10 16:53                       *
 *                                                            *
 *         Last Update : 2020/8/10 16:53                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
public class ApiController {


    private EchoService FeignService;

    @Autowired
    public void setFeignService(EchoService feignService) {
        FeignService = feignService;
    }

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable("str") String str) {
        return FeignService.echo(str);
    }
}
