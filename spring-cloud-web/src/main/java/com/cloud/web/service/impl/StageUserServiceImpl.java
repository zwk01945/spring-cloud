package com.cloud.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.bean.web.StageCateTemp;
import com.cloud.bean.web.StageUser;
import com.cloud.web.dao.StageUserDao;
import com.cloud.web.service.StageUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageUserServiceImpl.java                           *
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
@Service("stageUserService")
public class StageUserServiceImpl implements StageUserService {

    private final Logger log = LoggerFactory.getLogger(StageUserServiceImpl.class);

    StageUserDao stageUserDao;

    @Autowired
    public void setStageUserDao(StageUserDao stageUserDao) {
        this.stageUserDao = stageUserDao;
    }


    @Override
    public Map<Object, Object> findCateByUser(StageUser stageUser) {
        List<Map<String, Object>> cateByUser = stageUserDao.findCateByUser(stageUser);
        //拿到所有id为0的父类
        List<Map<String, Object>> parentId = cateByUser.stream().filter(item -> item.get("PARENT_ID").equals(0)).collect(Collectors.toList());
        //根据categoryid查询子类
        List<Object> slider = new ArrayList<Object>();
        for (Map<String, Object> parent : parentId) {
            StageCateTemp cateTemp = new StageCateTemp();
            cateTemp.setCategoryName(String.valueOf(parent.get("CATEGORY_NAME")));
            cateTemp.setRouteIcon(String.valueOf(parent.get("ROUTE_ICON")));
            cateTemp.setRouteUrl(String.valueOf(parent.get("ROUTE_URL")));
            List<Map<String, Object>> collect = cateByUser.stream().filter(item -> item.get("PARENT_ID").equals(parent.get("CATEGORY_ID"))).collect(Collectors.toList());
            if (collect.size() <= 0) {
                slider.add(cateTemp);
            }
        }
        Map<Object,Object> parent =(Map<Object,Object>) findChild(parentId, cateByUser);
        parent.put("单个",slider);
        return parent;
    }

    @Override
    public StageUser loginCheck(StageUser stageUser) {
        StageUser user = stageUserDao.loginCheck(stageUser);
        if (user.getUserPassword().equals(stageUser.getUserPassword())) {
            StageUser temp = new StageUser();
            temp.setId(user.getId());
            temp.setUserName(user.getUserName());
            temp.setUserId(user.getUserId());
            return temp;
        }
        return null;
    }


    private Map<Object,Object> findChild2(List<Map<String, Object>> child,List<Map<String, Object>> cateByUser,Object subName) {
        log.info("当前处理的是" + subName);
        List<Object> slider = new ArrayList<Object>();
        Map<Object,Object> param = new HashMap<>();
        log.info("循环当前分类下的子类");
        for (int i = 0; i < child.size(); i++) {
            //拿到每一个
            Map<String, Object> parent = child.get(i);
            log.info("第" + i + "个" + JSON.toJSONString(parent));
            //当前分类的名字
            Object categoryName = parent.get("CATEGORY_NAME");
            log.info("子类名称-----" + categoryName);
            //当前分类的子类
            List<Map<String, Object>> collect = cateByUser.stream().filter(item -> item.get("PARENT_ID").equals(parent.get("CATEGORY_ID"))).collect(Collectors.toList());
            if (collect.size() <= 0 ) {
                //当前分类没有子类
                slider.add(categoryName);
                param.put(subName,slider);
            } else {
                Map<Object, Object> child1 = findChild2(collect, cateByUser, categoryName);
//                log.info(categoryName + "---------------" + JSON.toJSONString(param));
                param.put(categoryName,child1);
            }
        }
        return param;
    }


    private Object findChild(List<Map<String, Object>> child,List<Map<String, Object>> cateByUser) {
        List<Object> slider = new ArrayList<Object>();
        Map<Object,Object> param = new HashMap<>();
        for (Map<String, Object> parent : child) {
            Object categoryName = parent.get("CATEGORY_NAME");
            Object route_icon = parent.get("ROUTE_ICON");
            StageCateTemp cateTemp = new StageCateTemp();
            cateTemp.setCategoryName(String.valueOf(categoryName));
            cateTemp.setRouteIcon(String.valueOf(route_icon));
            cateTemp.setRouteUrl(String.valueOf(parent.get("ROUTE_URL")));
            List<Map<String, Object>> collect = cateByUser.stream().filter(item -> item.get("PARENT_ID").equals(parent.get("CATEGORY_ID"))).collect(Collectors.toList());
            if (collect.size() <= 0) {
                slider.add(cateTemp);
            } else {
                Object child2 = findChild(collect, cateByUser);
                if (child2 instanceof Map) {
                    ((Map) child2).put("icon",String.valueOf(route_icon));
                } else if (child2 instanceof List) {
                    String s = String.valueOf(route_icon);
                    if (s != null && !s.equals("")) {
                        ((List) child2).add(s);
                    }
                }
                param.put(categoryName, child2);
            }
        }
        if (slider.size() == child.size()) {
            return  slider;
        } else {
            return param;
        }

    }

}
