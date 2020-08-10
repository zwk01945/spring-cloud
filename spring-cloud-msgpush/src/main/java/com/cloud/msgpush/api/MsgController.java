package com.cloud.msgpush.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
public class MsgController {

        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
            return string;
        }

}
