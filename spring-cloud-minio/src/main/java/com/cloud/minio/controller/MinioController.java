package com.cloud.minio.controller;

import com.alibaba.fastjson.JSON;
import com.cloud.bean.ResultObject;
import com.cloud.minio.config.MinioConfig;
import com.cloud.minio.config.MinioProperties;
import com.cloud.minio.service.ApiUpload;
import com.cloud.controller.BaseController;
import com.cloud.util.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : MinioController.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 12:58                       *
 *                                                            *
 *         Last Update : 2020/8/3 12:58                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   图片文件服务器控制器                                         *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@RestController
public class MinioController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MinioController.class);

    ApiUpload apiUpload;

    MinioProperties minioProperties;

    MinioConfig minioConfig;

    @Autowired
    public void setMinioConfig(MinioConfig minioConfig) {
        this.minioConfig = minioConfig;
    }

    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Autowired
    public void setApiUpload(ApiUpload apiUpload) {
        this.apiUpload = apiUpload;
    }

    @RequestMapping(value = "/minio/config")
    public Object upload(){
        return JSON.toJSONString(minioProperties);
    }

    @RequestMapping(value = "/minio/refresh")
    public Object refresh(){
        return minioConfig.envListener();
    }


    @RequestMapping(value = "/minio/uploadStream",method = RequestMethod.POST)
    public ResultObject uploadStream( @RequestParam("bucket") String bucket,
                              @RequestParam("obj") String objName,
                                      @RequestParam("file")MultipartFile uploadImage) throws IOException {

        InputStream inputStream = uploadImage.getInputStream();
        String contentType = uploadImage.getContentType();
        boolean bkt = StringUtils.isEmpty(bucket);
        boolean obj = StringUtils.isEmpty(objName);
        boolean cty = StringUtils.isEmpty(contentType);
        if ( bkt || obj || cty) return new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
        boolean process = apiUpload.uploadStream(bucket, objName, inputStream, contentType);
        return new ResultObject(HttpStatus.OK.value(),String.valueOf(process),"");
    }

    @RequestMapping(value = "/minio/upload")
    public ResultObject upload(@RequestParam("path") String filePath,
                               @RequestParam("bucket") String bucket,
                               @RequestParam("obj") String objName,
                               HttpServletRequest request) {
        String contentType = request.getContentType();
        boolean file = StringUtils.isEmpty(filePath);
        boolean bkt = StringUtils.isEmpty(bucket);
        boolean obj = StringUtils.isEmpty(objName);
        boolean cty = StringUtils.isEmpty(contentType);
        if (file || bkt || obj || cty) return new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
        boolean upload = apiUpload.upload(filePath, bucket, objName,contentType);
        return new ResultObject(HttpStatus.OK.value(),String.valueOf(upload),upload);
    }

    @RequestMapping(value = "/minio/download")
    public void read(@RequestParam("path") String filePath,@RequestParam("bucket") String bucket,
                     @RequestParam("obj") String objName, HttpServletResponse response) {
        apiUpload.download(filePath,bucket, objName);
    }

    @RequestMapping(value = "/minio/url")
    public String url(@RequestParam("bucket") String bucket,
                     @RequestParam("obj") String objName) {
       return apiUpload.getUri(bucket, objName);
    }


    @RequestMapping(value = "/minio/obj")
    public ResultObject uploadByObject(@RequestParam("path") String filePath,
                               @RequestParam("bucket") String bucket,
                               @RequestParam("obj") String objName,
                               HttpServletRequest request) {
        String contentType = request.getContentType();
        boolean file = StringUtils.isEmpty(filePath);
        boolean bkt = StringUtils.isEmpty(bucket);
        boolean obj = StringUtils.isEmpty(objName);
        boolean cty = StringUtils.isEmpty(contentType);
        if (file || bkt || obj || cty) return new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
        boolean upload = apiUpload.uploadByObject(filePath, bucket, objName,contentType);
        return new ResultObject(HttpStatus.OK.value(),String.valueOf(upload),upload);
    }

    @RequestMapping(value = "/minio/remove")
    public ResultObject removeObject(@RequestParam("bucket") String bucket,
                      @RequestParam("obj") String objName) {
        apiUpload.removeObject(bucket, objName);
        return new ResultObject(HttpStatus.OK.value());
    }

    @RequestMapping(value = "/minio/downloadStream")
    public void read(@RequestParam("bucket") String bucket,
                             @RequestParam("obj") String objName,HttpServletResponse response) throws IOException {
        InputStream in = null;
        ServletOutputStream out  = response.getOutputStream();;
        try {
            in = apiUpload.read(bucket, objName);
            response.setContentType("application/zip;charset=utf-8");//告诉客户端用utf-8解析excel，同时设置编码格式
            response.setHeader("Content-Disposition", "attachment;fileName="+ objName);
            if (in != null) {
                FileUtil.writeAsStream(in,out);
            }
        } catch (Exception e) {
            ResultObject resultObject = new ResultObject(HttpStatus.INTERNAL_SERVER_ERROR.value(),"下载文件异常","");
            out.print(JSON.toJSONString(resultObject));
            out.close();
        }
    }

}
