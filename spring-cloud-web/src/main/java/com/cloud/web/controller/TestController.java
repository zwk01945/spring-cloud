package com.cloud.web.controller;

import com.cloud.bean.ResultObject;
import com.cloud.web.feign.MasterService;
import com.cloud.web.feign.SlaveService;
import com.cloud.web.service.Aservice;
//import io.seata.core.context.RootContext;
//import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : TestController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/19 12:52                       *
 *                                                            *
 *         Last Update : 2020/8/19 12:52                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    Aservice aservice;
    @RequestMapping("/test")
    public ResultObject test() throws Exception{
        aservice.insert();
        return new ResultObject(0,"success",null);
    }

}
