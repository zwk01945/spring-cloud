package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : BaseController.java                    *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   基础控制器作为模块依赖的父类                                  *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Component
public class BaseController {


    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    @Autowired
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    //下面为公共方法  可以入库操作表等

}
