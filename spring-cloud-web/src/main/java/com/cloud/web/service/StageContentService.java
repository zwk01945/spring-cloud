package com.cloud.web.service;

import com.cloud.bean.web.StageContent;

import java.util.List;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageContentService.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/29 15:50                       *
 *                                                            *
 *         Last Update : 2020/9/29 15:50                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public interface StageContentService {

    List<StageContent> findList(Integer cateId);

}
