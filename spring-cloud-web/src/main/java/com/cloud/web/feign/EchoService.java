package com.cloud.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@FeignClient(name = "spring-cloud-msgpush")
public interface EchoService {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}
