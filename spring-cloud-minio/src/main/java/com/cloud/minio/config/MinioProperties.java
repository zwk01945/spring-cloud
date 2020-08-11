package com.cloud.minio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : MinioProperties.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 9:42                       *
 *                                                            *
 *         Last Update : 2020/8/3 9:42                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   minio服务器配置                                            *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    public String endPoint;
    public String accessKey;
    public String secretKey;
    public String policyPath;


    public String getPolicyPath() {
        return policyPath;
    }

    public void setPolicyPath(String policyPath) {
        this.policyPath = policyPath;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
