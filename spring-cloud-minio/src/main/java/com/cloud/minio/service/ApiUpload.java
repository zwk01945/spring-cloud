package com.cloud.minio.service;

import java.io.InputStream;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : ApiUpload.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 12:49                       *
 *                                                            *
 *         Last Update : 2020/8/3 12:49                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   上传文件到图片服务器接口                                     *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public interface ApiUpload {

    boolean upload(String filePath,String bucket,String objName,String contentType);

    boolean uploadByObject(String filePath,String bucket,String objName,String contentType);

    InputStream  read(String bucket, String objName);

    void  download(String filePath, String bucket, String objName);

    String getUri(String bucket, String objName);

    void removeBucket(String bucket);

    void removeObject(String bucket,String objName);
}
