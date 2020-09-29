package com.cloud.web.controller;

import com.alibaba.fastjson.JSON;
import com.cloud.bean.ResultObject;
import com.cloud.bean.web.StageContent;
import com.cloud.bean.web.StageUser;
import com.cloud.controller.BaseController;
import com.cloud.web.service.Aservice;
import com.cloud.web.service.StageContentService;
import com.cloud.web.service.StageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/21 16:34                       *
 *                                                            *
 *         Last Update : 2020/9/21 16:34                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
@RequestMapping("/stage")
public class StageController extends BaseController {


    @Autowired
    StageUserService stageUserService;

    @Autowired
    StageContentService stageContentService;

    //暂定登陆写在当前  之后挪到auth
    @PostMapping("/login")
    public ResultObject login(@RequestBody StageUser stageUser) throws Exception{
        StageUser stageUser1 = stageUserService.loginCheck(stageUser);
        ResultObject resultObject = new ResultObject();
        if (stageUser1 != null){
            resultObject.setCode(0);
            resultObject.setMsg("success");
            resultObject.setData(stageUser1);
        }else {
            resultObject.setCode(-1);
            resultObject.setMsg("fail");
        }
        return resultObject;
    }


    @RequestMapping("/cate")
    public ResultObject findCateByUser(StageUser stageUser) throws Exception{
        Map<Object, Object> cateByUser = stageUserService.findCateByUser(stageUser);
        return new ResultObject(0,"success",cateByUser);
    }

    @RequestMapping("/content")
    public ResultObject findCateByUser(@RequestParam("cateid") Integer cateID) throws Exception{
        List<StageContent> list = stageContentService.findList(cateID);
        return new ResultObject(0,"success",list);
    }

}
