package com.cloud.bean.msg;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : TemplateCode.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 9:44                       *
 *                                                            *
 *         Last Update : 2020/8/11 9:44                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   短信接口模板code,从redis中获取，定时向redis更新数据            *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class  TemplateCode {

    private String codeName;
    private Integer id;

    public static TemplateCode getInstance() {
        return new TemplateCode();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
