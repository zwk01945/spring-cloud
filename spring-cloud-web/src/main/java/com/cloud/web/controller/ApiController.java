//package com.cloud.web;
//
//import com.cloud.aop.annotation.CacheRedis;
//import com.cloud.bean.ResultObject;
//import com.aliyuncs.CommonResponse;
//import com.cloud.web.feign.EchoService;
//import com.cloud.web.feign.SocketService;
//import com.cloud.web.service.Aservice;
//import com.cloud.web.service.AserviceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.*;
//
///**************************************************************
// ***       S  T  A  G  E    多模块依赖项目                    ***
// **************************************************************
// *                                                            *
// *         Project Name : cloud                               *
// *                                                            *
// *         File Name : ApiController.java                     *
// *                                                            *
// *         Programmer : Mr.zhang                              *
// *                                                            *
// *         Start Date : 2020/8/10 16:53                       *
// *                                                            *
// *         Last Update : 2020/8/10 16:53                      *
// *                                                            *
// *------------------------------------------------------------*
// * Functions:                                                 *
// *   Get_Build_Frame_Count -- Fetches the number of frames in *
// * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
//@RestController
//public class ApiController {
//
//    private EchoService echoService;
//
//    private SocketService socketService;
//    @Autowired
//    Aservice aservice;
//
//    @Autowired
//    public void setSocketService(SocketService socketService) {
//        this.socketService = socketService;
//    }
//
//    @Autowired
//    public void setEchoService(EchoService echoService) {
//        this.echoService = echoService;
//    }
//
//
//    @GetMapping(value = "/echo/{str}")
//    public String echo(@PathVariable("str") String str) {
//        return echoService.echo(str);
//    }
//    @PostMapping(value = "/msg/send")
//    public CommonResponse send(@RequestParam("phone") String phone, @RequestParam("data") String data, @RequestParam("code") String code){
//       return echoService.send(phone,data,code);
//    }
//
//    @PostMapping(value = "/msg/query")
//    public CommonResponse querySendDetails(@RequestParam("phone")String phone,@RequestParam("date") String date,
//                                    @RequestParam("currentPage")String currentPage,
//                                    @RequestParam("pageSize")String pageSize){
//        return echoService.querySendDetails(phone,date,currentPage,pageSize);
//    }
//
//    @GetMapping("/socket/send")
//    public ResultObject sendMessage(@RequestParam("name") String name, @RequestParam("message") String message){
//       return socketService.sendMessage(name,message);
//    }
//
//    @GetMapping("/socket/sendAll")
//    ResultObject sendAllMessage(@RequestParam("message") String message){
//        return socketService.sendAllMessage(message);
//    }
//    @GetMapping("/redis")
//    public void get1() {
//        aservice.get();
//    }
//
//}
