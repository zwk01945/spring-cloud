package com.cloud.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.bean.web.StageContent;
import com.cloud.web.dao.StageContentDao;
import com.cloud.web.service.StageCategoryService;
import com.cloud.web.service.StageContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageContentServiceImpl.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/29 15:51                       *
 *                                                            *
 *         Last Update : 2020/9/29 15:51                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Service("stageContentService")
public class StageContentServiceImpl implements StageContentService {


    StageContentDao stageContentDao;

    @Autowired
    public void setStageContentDao(StageContentDao stageContentDao) {
        this.stageContentDao = stageContentDao;
    }

    @Override
    public List<StageContent> findList(Integer cateId) {
        Map<String,Object> param = new HashMap<>();
        param.put("CATE_ID",cateId);
        QueryWrapper<StageContent> wrapper = new QueryWrapper<StageContent>();
        wrapper.allEq(param);
        return stageContentDao.selectList(wrapper);
    }
}
