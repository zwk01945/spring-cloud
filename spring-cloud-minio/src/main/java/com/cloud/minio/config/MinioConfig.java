package com.cloud.minio.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.messages.Bucket;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloud.util.file.FileUtil;
import java.io.File;
import java.util.List;
import java.util.Properties;


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
public class MinioConfig implements InitializingBean, ApplicationContextAware,CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${nacos.serveraddr:#{null}}")
    private String SERVER_ADDR;
    @Value("${nacos.dataid:#{null}}")
    private String DATA_ID;
    @Value("${nacos.group:#{null}}")
    private String GROUP;

    public MinioProperties minioProperties;

    public ConfigService configService;

    public MinioClient build;

    public ApplicationContext applicationContext;


    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("data_id,group:{},{}",DATA_ID,GROUP);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
        configService = NacosFactory.createConfigService(properties);
        logger.info("初始化添加nacos listener.....");
        logger.info("data_id,group:{},{}",DATA_ID,GROUP);
        String config = configService.getConfig(DATA_ID, GROUP, 5000);
        System.out.println(config);
        build = MinioClient.builder()
                .endpoint(minioProperties.getEndPoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("初始化添加nacos listener.....");
        logger.info("data_id,group:{},{}",DATA_ID,GROUP);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
    }




    @Bean
    public  MinioClient getMinioClient() throws Exception {
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
