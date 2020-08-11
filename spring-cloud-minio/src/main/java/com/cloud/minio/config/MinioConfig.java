package com.cloud.minio.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.messages.Bucket;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloud.util.file.FileUtil;
import java.io.File;
import java.util.List;


/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : MinioConfig.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/3 13:36                       *
 *                                                            *
 *         Last Update : 2020/8/3 13:36                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   Get_Build_Frame_Count -- Fetches the number of frames in *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
public class MinioConfig {

    private final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    MinioProperties minioProperties;

    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    public  MinioClient getMinioClient() throws Exception {
        logger.info("初始化minio服务器配置...");
        MinioClient build = MinioClient.builder()
                .endpoint(minioProperties.getEndPoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
        List<Bucket> buckets = build.listBuckets();
        if (buckets.size() > 0) {
            File file = FileUtil.creatFile(minioProperties.getPolicyPath());
            String content = FileUtils.readFileToString(file, "UTF-8");
            logger.info("读取本地Bucket的json配置文件...完成");
            JSONObject jsonObject = JSON.parseObject(content);
            for (Bucket bucket:buckets) {
                String config = JSON.toJSONString(jsonObject.get(bucket.name()));
                if (!config.equals("null")) {
                    build.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket.name()).config(config).build());
                    logger.info("bucket --->" + bucket.name() + "权限设置完成");
                }
            }
        }
        return build;

    }
}
