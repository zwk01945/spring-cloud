package com.cloud.gateway;

import com.cloud.gateway.routes.AutoRouteConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : Refresh.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/3 10:42                       *
 *                                                            *
 *         Last Update : 2020/9/3 10:42                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
public class Refresh {

    AutoRouteConfig autoRouteConfig;

    @Autowired
    public void setAutoRouteConfig(AutoRouteConfig autoRouteConfig) {
        this.autoRouteConfig = autoRouteConfig;
    }

    @GetMapping("/gateway/refresh")
    public void refreshConfig() {
        autoRouteConfig.envListener();
    }


}
