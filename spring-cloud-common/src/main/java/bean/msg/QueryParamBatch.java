package bean.msg;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : QueryParamBatch.java                   *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/30 14:15                       *
 *                                                            *
 *         Last Update : 2020/7/30 14:15                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   批量查询参数类                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class QueryParamBatch {

    private String[] phoneNumber;
    private String[] signName;
    private String[] templateParam;
    private String[] smsUpExtendCode;


    public String[] getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String[] phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String[] getSignName() {
        return signName;
    }

    public void setSignName(String[] signName) {
        this.signName = signName;
    }

    public String[] getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String[] templateParam) {
        this.templateParam = templateParam;
    }

    public String[] getSmsUpExtendCode() {
        return smsUpExtendCode;
    }

    public void setSmsUpExtendCode(String[] smsUpExtendCode) {
        this.smsUpExtendCode = smsUpExtendCode;
    }
}
