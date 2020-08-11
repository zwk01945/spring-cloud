package com.cloud.msgpush.api;

import com.cloud.bean.ResultObject;
import com.cloud.msgpush.socket.WebsocketHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : SocketController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 13:14                       *
 *                                                            *
 *         Last Update : 2020/8/11 13:14                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   socket服务                                                *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
@RequestMapping("/socket")
public class SocketController {

    @GetMapping("/send")
    public ResultObject sendMessage(@RequestParam("name") String name, @RequestParam("message") String message) {
        boolean result = false;
        try {
            result = WebsocketHandler.sendMessage(name, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObject(200,"success",result);
    }

    @GetMapping("/sendAll")
    public ResultObject sendAllMessage(@RequestParam("message") String message) {
        boolean result = false;
        try {
            result = WebsocketHandler.sendAllMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObject(200,"success",result);
    }
}
