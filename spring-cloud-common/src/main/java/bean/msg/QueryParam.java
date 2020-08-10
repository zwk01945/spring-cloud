package bean.msg;

import java.util.HashMap;
import java.util.Map;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : QueryParam.java                        *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/30 12:55                       *
 *                                                            *
 *         Last Update : 2020/7/30 12:55                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   短信发送参数类                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class QueryParam {

    /** 手机号 */
    private String phoneNumber;
    /** 模板内容 */
    private Map<String,Object> templateParam;
    /** 发送日期yyyyMMdd */
    private String sendDate;
    /** 查询页数数量 */
    private String pageSize;
    /** 当前页 */
    private String currentPage;


    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Object> getTemplateParam() {
        if (templateParam == null) templateParam = new HashMap<>();
        return templateParam;
    }

    public void setTemplateParam(Map<String, Object> templateParam) {
        this.templateParam = templateParam;
    }
}
