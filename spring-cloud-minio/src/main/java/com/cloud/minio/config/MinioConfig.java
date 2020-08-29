package com.cloud.minio.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import io.minio.GetBucketPolicyArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import io.minio.messages.Bucket;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.annotation.Configuration;
import com.cloud.util.file.FileUtil;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;


/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base             *
 *                                                            *
 *         File Name : MinioConfig.java                       *
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
//@RefreshScope
public class MinioConfig  implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${nacos.serveraddr:#{null}}")
    private String SERVER_ADDR;
    @Value("${nacos.dataid:#{null}}")
    private String DATA_ID;
    @Value("${nacos.group:#{null}}")
    private String GROUP;


    private String SERVER_ADDR_OLD;
    private String DATA_ID_OLD;
    private String GROUP_OLD;

    public static MinioProperties minioProperties;

    public static ConfigService configService;

    public static Listener listener;

    private static ThreadLocal<MinioClient>  build = new ThreadLocal<MinioClient>();

    private static ThreadLocal<String> defaultPolicy = new ThreadLocal<>();

    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        MinioConfig.minioProperties = minioProperties;
    }

    public String getSERVER_ADDR_OLD() {
        return SERVER_ADDR_OLD;
    }

    public void setSERVER_ADDR_OLD(String SERVER_ADDR_OLD) {
        this.SERVER_ADDR_OLD = SERVER_ADDR_OLD;
    }

    public String getDATA_ID_OLD() {
        return DATA_ID_OLD;
    }

    public void setDATA_ID_OLD(String DATA_ID_OLD) {
        this.DATA_ID_OLD = DATA_ID_OLD;
    }

    public String getGROUP_OLD() {
        return GROUP_OLD;
    }

    public void setGROUP_OLD(String GROUP_OLD) {
        this.GROUP_OLD = GROUP_OLD;
    }

    public static MinioClient client() {
        MinioClient client = build.get();
        if (client == null) {
            client =  MinioClient.builder()
                    .endpoint(minioProperties.getEndPoint())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
        }
        return client;
    }

    /**
     * 该监听出现在afterPropertiesSet之后
     * 用于处理实时更新配置信息
     * @param event
     */
    @EventListener
    public void envListener(EnvironmentChangeEvent event) throws Exception {
        logger.info("监听到工程properties配置动态变化");
        Set<String> keys = event.getKeys();
        long minioCount = keys.stream().filter(key -> key.startsWith(MinioConstant.START_WITH_MINIO)).count();
        long nacosCount = keys.stream().filter(key -> key.startsWith(MinioConstant.START_WITH_NACOS)).count();
        if (nacosCount > 0 || minioCount > 0) {
            //需要重新绑定监听特殊文件
            logger.info("data_id,group:{},{}",DATA_ID,GROUP);
            setDATA_ID_OLD(DATA_ID);
            setGROUP_OLD(GROUP);
            setSERVER_ADDR_OLD(SERVER_ADDR);
            logger.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
            ConfigService service = NacosFactory.createConfigService(properties);

            //只要侦听到涉及到client和service配置发生了变化就需要刷新
            if (configService != null && listener != null){
                logger.info("移除旧的监听..."+DATA_ID_OLD + GROUP_OLD);
                configService.removeListener(DATA_ID_OLD,GROUP_OLD,listener);
            }
//            configService = service;

//            creatListener();

            //需要刷新client
//            MinioClient client =  MinioClient.builder()
//                    .endpoint(minioProperties.getEndPoint())
//                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
//                    .build();
//            build.set(client);
//            //重新拉取权限配置
//            buildFromNacos();
        }

    }


    private void creatListener () throws NacosException {
        logger.info("添加新nacos侦听器.....");
        listener = new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("nacos服务器下发minioPolicy安全配置json数据.....");
                logger.info("data_id,group:{},{}", DATA_ID, GROUP);
                logger.info("接收到的数据为:{}", configInfo);
                receivePolicy(configInfo);
            }
        };
        configService.addListener(DATA_ID, GROUP, listener);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("minio服务启动，初始化configService.....");

        logger.info("data_id,group:{},{}",DATA_ID,GROUP);
        setDATA_ID_OLD(DATA_ID);
        setGROUP_OLD(GROUP);
        setSERVER_ADDR_OLD(SERVER_ADDR);
        logger.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR,SERVER_ADDR);
        configService = NacosFactory.createConfigService(properties);
        creatListener();

        logger.info("拉取"+DATA_ID+"配置.....");
        buildFromNacos();

    }

    /**
     * 设置参数
     * @param client
     * @param dPolicy
     * @throws Exception
     */
    private void setThreadlocal(MinioClient client,String dPolicy) throws Exception {
        build.set(client);
        defaultPolicy.set(dPolicy);
        client.listBuckets().forEach(bucket -> {
            try {
                logger.info(bucket.name() + "配置的权限为:{}",build.get().getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucket.name()).build()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        logger.info("默认的权限为:{}",defaultPolicy.get());
    }

    /**
     * 启动服务默认从nacos拉取最新配置
     * 由于是启动服务时候拉取，不考虑线程安全
     * @throws Exception
     */
    private void buildFromNacos() throws Exception {
        logger.info("从nacos服务器读取minioPolicy安全配置json数据.....");
        logger.info("data_id,group:{},{}",DATA_ID,GROUP);
        String nacosConfig = configService.getConfig(DATA_ID, GROUP, 5000);
        logger.info("读取Bucket的json配置文件.....完成");
        MinioClient client = client();
        List<Bucket> buckets =  client.listBuckets();
        if (buckets.size() > 0 && !StringUtils.isEmpty(nacosConfig)) {
            JSONObject jsonObject = JSON.parseObject(nacosConfig);
            String dPolicy = JSON.toJSONString(jsonObject.get("default"));
            for (Bucket bucket:buckets) {
                String config = JSON.toJSONString(jsonObject.get(bucket.name()));
                if (!config.equals("null")) {
                    client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket.name()).config(config).build());
                    logger.info("bucket --->" + bucket.name() + "权限设置完成");
                } else {
                    logger.info("未找到"+bucket.name() + "桶的安全配置,将设置默认权限");
                    client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket.name()).config(dPolicy).build());
                }
            }
            setThreadlocal(client,dPolicy);
        }
    }


    /**
     * 读取本地json
     * @return
     * @throws Exception
     */
    private String policyJson() throws Exception {
            File file = FileUtil.creatFile(minioProperties.getPolicyPath());
            return FileUtils.readFileToString(file, "UTF-8");
    }

    /**
     * 给指定的bucket设置权限
     * @param bucket
     * @throws Exception
     */
    public static void setDefaultPolicy(String bucket) throws Exception {
        String config = defaultPolicy.get();
        MinioConfig.client().setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(config).build());
            logger.info("bucket --->" + bucket + "默认只读权限设置完成");
    }

    /**
     * 服务器更新信息
     * @param configInfo
     */
    private void receivePolicy(String configInfo){
        try {
            MinioClient client = client();
            List<Bucket> buckets = client.listBuckets();
            if (buckets.size() > 0 && !StringUtils.isEmpty(configInfo)) {
                JSONObject jsonObject = JSON.parseObject(configInfo);
                String dPolicy = JSON.toJSONString(jsonObject.get("default"));
                for (Bucket bucket:buckets) {
                    String config = JSON.toJSONString(jsonObject.get(bucket.name()));
                    if (!config.equals("null")) {
                        client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket.name()).config(config).build());
                        logger.info("bucket --->" + bucket.name() + "权限设置完成");
                    } else {
                        logger.info("设置"+bucket.name() + "桶的安全配置为默认");
                        client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket.name()).config(dPolicy).build());
                    }
                }
                setThreadlocal(client,dPolicy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
