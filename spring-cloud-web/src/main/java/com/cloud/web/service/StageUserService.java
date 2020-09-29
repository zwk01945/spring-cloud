package com.cloud.web.service;

import com.cloud.bean.web.StageUser;
import java.util.Map;
/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageUserService.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/21 16:23                       *
 *                                                            *
 *         Last Update : 2020/9/21 16:23                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public interface StageUserService {
    Map<Object, Object> findCateByUser(StageUser StageUser);

    StageUser loginCheck(StageUser StageUser);
}
