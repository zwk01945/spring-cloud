package com.cloud.bean.web;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : StageCateTemp.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/9/23 10:43                       *
 *                                                            *
 *         Last Update : 2020/9/23 10:43                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class StageCateTemp {
    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 路由地址URL
     */
    private String routeUrl;

    /**
     * 路由图标
     */
    private String routeIcon;

    public StageCateTemp() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getRouteIcon() {
        return routeIcon;
    }

    public void setRouteIcon(String routeIcon) {
        this.routeIcon = routeIcon;
    }
}
