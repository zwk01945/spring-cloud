package com.cloud.web.service.impl;

import com.cloud.bean.web.StageCategory;
import com.cloud.web.dao.StageCategoryDao;
import com.cloud.web.service.StageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageCategoryServiceImpl.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/21 16:11                       *
 *                                                            *
 *         Last Update : 2020/9/21 16:11                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Service("stageCategoryService")
public class StageCategoryServiceImpl implements StageCategoryService {

    StageCategoryDao stageCategoryDao;

    @Autowired
    public void setStageCategoryDao(StageCategoryDao stageCategoryDao) {
        this.stageCategoryDao = stageCategoryDao;
    }

}
