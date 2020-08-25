package com.cloud.msgpush.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : SmsEnv.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/29 15:38                       *
 *                                                            *
 *         Last Update : 2020/7/29 15:38                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   阿里云短信推送参数类                                         *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
@RefreshScope
public class SmsEnv {

    private String accessKeyId;
    private String accessKeySecret;
    private String templateCode;
    private String signName;
    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
