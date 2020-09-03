package com.cloud.minio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

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
@Component
@RefreshScope
public class MinioProperties {

    @Value("${minio.endPoint:#{null}}")
    public  String endPoint;
    @Value("${minio.accessKey:#{null}}")
    public  String accessKey;
    @Value("${minio.secretKey:#{null}}")
    public  String secretKey;
    @Value("${minio.policyPath:#{null}}")
    public  String policyPath;

    @Value("${nacos.serveraddr:#{null}}")
    public  String serverAddr;
    @Value("${nacos.dataid:#{null}}")
    public  String dataId;
    @Value("${nacos.group:#{null}}")
    public   String group;


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

    public String getPolicyPath() {
        return policyPath;
    }

    public void setPolicyPath(String policyPath) {
        this.policyPath = policyPath;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
