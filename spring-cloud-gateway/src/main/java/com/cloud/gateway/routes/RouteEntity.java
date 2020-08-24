package com.cloud.gateway.routes;

import java.util.ArrayList;
import java.util.List;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : RouteEntity.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/24 16:45                       *
 *                                                            *
 *         Last Update : 2020/8/24 16:45                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class RouteEntity {

    /**
     * 路由id
     */
    private String id;

    /**
     * 路由断言集合
     */
    private List<PredicateEntity> predicates = new ArrayList<>();

    /**
     * 路由过滤器集合
     */
    private List<FilterEntity> filters = new ArrayList<>();

    /**
     * 路由转发的目标uri
     */
    private String uri;

    /**
     * 路由执行的顺序
     */
    private int order = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PredicateEntity> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<PredicateEntity> predicates) {
        this.predicates = predicates;
    }

    public List<FilterEntity> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterEntity> filters) {
        this.filters = filters;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
