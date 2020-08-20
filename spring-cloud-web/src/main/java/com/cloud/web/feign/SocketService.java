package com.cloud.web.feign;

import com.cloud.bean.ResultObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud                               *
 *                                                            *
 *         File Name : SocketService.java                     *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 14:13                       *
 *                                                            *
 *         Last Update : 2020/8/11 14:13                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
//@FeignClient(name = "spring-cloud-msgpush")
public interface SocketService {
    /**
     * socket服务
     * @param name
     * @param message
     * @return
     */
    @GetMapping("/socket/send")
    ResultObject sendMessage(@RequestParam("name") String name, @RequestParam("message") String message);

    @GetMapping("/socket/sendAll")
    ResultObject sendAllMessage(@RequestParam("message") String message);

}
