package com.cloud.minio.service.impl;


import com.cloud.minio.config.MinioConfig;
import com.cloud.minio.config.MinioProperties;
import com.cloud.minio.service.ApiUpload;
import io.minio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static com.cloud.minio.config.MinioConfig.setDefaultPolicy;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : MinioFileUploader.java                 *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 9:35                       *
 *                                                            *
 *         Last Update : 2020/8/3 9:35                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   minio 客户端                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Service("apiUpload")
public class MinioFileUploader implements ApiUpload {

    private final Logger logger = LoggerFactory.getLogger(MinioFileUploader.class);

    public Lock lock = new ReentrantLock();

    MinioProperties minioProperties;

    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }


    @Override
    public void download(String filePath, String bucket, String objName) {
        try {
            MinioConfig.client().downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucket)
                            .object(objName)
                            .filename(filePath)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean upload(String filePath, String bucket, String objName,String contentType) {
        boolean process = false;
        try {
            lock.lock();
            InputStream inputStream = new FileInputStream(filePath);
            boolean isExist = MinioConfig.client().bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (isExist){
                logger.info("重复的bucket存在,覆盖操作");
            }else {
                MinioConfig.client().makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket).build());
                logger.info("添加新的bucket到服务器,key为:{}",bucket);
                logger.info("判断是否创建子目录.....");
                setDefaultPolicy(bucket);
            }
            process = reUpload(bucket,objName,inputStream,contentType);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return process;
    }

    @Override
    public boolean uploadByObject(String filePath, String bucket, String objName, String contentType) {

        boolean process = false;
        try {
            lock.lock();
            boolean isExist = MinioConfig.client().bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (isExist){
                logger.info("重复的bucket存在,覆盖操作");
            }else {
                MinioConfig.client().makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket).build());
                logger.info("添加新的bucket到服务器,key为:{}",bucket);
                setDefaultPolicy(bucket);
            }
            MinioConfig.client().uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(objName)
                            .filename(filePath)
                            .contentType(contentType)
                            .build());
            process = true;
        } catch (Exception e) {
            e.printStackTrace();
            removeBucket(bucket);
        } finally {
            lock.unlock();
        }
        return process;
    }

    @Override
    public InputStream read(String bucket, String objName) {
        InputStream stream = null;
        try {
            stream = MinioConfig.client().getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objName)
                            .build());
            //读到流
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stream;
    }

    public String getUri(String bucket, String objName) {
        String objectUrl = null;
        try {
            objectUrl = MinioConfig.client().getObjectUrl(bucket, objName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectUrl;
    }

    @Override
    public void removeBucket(String bucket) {
        try {
            boolean isExist = MinioConfig.client().bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (isExist) {
                logger.info("删除已经创建的bucket");
                MinioConfig.client().removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeObject(String bucket, String objName) {
        try {
            lock.lock();
            MinioConfig.client().removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(objName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private boolean reUpload (String bucket,String objName,InputStream inputStream,String contentType) throws Exception {

        String[] split = objName.split("/");
        if (objName.endsWith("/")) {
            MinioConfig.client().putObject(
                    PutObjectArgs.builder().bucket(bucket).object(objName).stream(
                            new ByteArrayInputStream(new byte[] {}), 0, -1).build());
            logger.info("只创建目录.....成功");
            return true;
        } else if (split.length > 1){
            String dir = objName.substring(0,objName.lastIndexOf("/") + 1);
            MinioConfig.client().putObject(
                    PutObjectArgs.builder().bucket(bucket).object(dir).stream(
                            new ByteArrayInputStream(new byte[] {}), 0, -1).build());
            logger.info("创建目录并存放至该目录下.....成功");
        }
        MinioConfig.client().putObject(PutObjectArgs.builder().bucket(bucket).object(objName).stream(
                inputStream, -1, 10485760).contentType(contentType).build());
        return true;
    }

//    private void setDefaultPolicy(String bucket) throws Exception {
//        File file = FileUtil.creatFile(minioProperties.getPolicyPath());
//        String content = FileUtils.readFileToString(file, "UTF-8");
//        logger.info("读取本地Bucket的json配置文件...完成");
//        JSONObject jsonObject = JSON.parseObject(content);
//        String config = JSON.toJSONString(jsonObject.get("default"));
//        System.out.println(config);
//        if (!config.equals("null")) {
//            MinioConfig.client().setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(config).build());
//            logger.info("bucket --->" + bucket + "默认只读权限设置完成");
//        }
//    }


}
