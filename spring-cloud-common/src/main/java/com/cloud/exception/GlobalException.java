package com.cloud.exception;

import com.cloud.bean.ResultObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : GlobalException.java                   *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   全局异常拦截                                               *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(MyException.class)
    public Object customerHandler(MyException e) {
        return new ResultObject(10000);
    }


    @ExceptionHandler(RuntimeException.class)
    public Object nullPointHandler(RuntimeException e) {
        e.printStackTrace();
        return new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @ExceptionHandler(Exception.class)
    public Object ExceptionPointHandler(Exception e) {
        e.printStackTrace();
        return new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
