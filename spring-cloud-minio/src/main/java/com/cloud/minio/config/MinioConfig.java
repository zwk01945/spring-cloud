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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.cloud.util.file.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.List;
import java.util.Properties;
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
 *   实现从Nacos动态修改配置,程序资源自动加载,包括刷新Minio的密钥配置  *
 *   以及外置minio.json的配置信息                                *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Configuration
public class MinioConfig  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    private String SERVER_ADDR_OLD;
    private String DATA_ID_OLD;
    private String GROUP_OLD;

    public static final ThreadLocal<ConfigService> configService = new ThreadLocal<>();

    private static final ThreadLocal<MinioClient>  build = new ThreadLocal<MinioClient>();

    private static final ThreadLocal<String> defaultPolicy = new ThreadLocal<>();

    MinioProperties minioProperties;
    @Autowired
    public void setMinioProperties(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
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


    public  MinioClient client() throws Exception{
        MinioClient client = build.get();
        if (client == null) {
                logger.info("重新创建新的MinClient");
                logger.info("endpoint,accesskey,secretkey:{},{},{}",minioProperties.getEndPoint(),minioProperties.getAccessKey(),minioProperties.getSecretKey());
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
     * @param
     * @return
     */
//    @EventListener EnvironmentChangeEvent event
    public boolean envListener() {

        logger.info("监听到工程properties配置动态变化");
//        Set<String> keys = event.getKeys();
//        long minioCount = keys.stream().filter(key -> key.startsWith(MinioConstant.START_WITH_MINIO)).count();
//        long nacosCount = keys.stream().filter(key -> key.startsWith(MinioConstant.START_WITH_NACOS)).count();
//        if (nacosCount > 0 || minioCount > 0) {
            try {
                //需要重新绑定监听特殊文件
                logger.info("data_id,group:{},{}", minioProperties.getDataId(), minioProperties.getGroup());
                setDATA_ID_OLD(minioProperties.getDataId());
                setGROUP_OLD(minioProperties.getGroup());
                setSERVER_ADDR_OLD(minioProperties.getServerAddr());
                logger.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);
                Properties properties = new Properties();
                properties.setProperty(PropertyKeyConst.SERVER_ADDR, minioProperties.getServerAddr());
                ConfigService service = NacosFactory.createConfigService(properties);
//            //只要侦听到涉及到client和service配置发生了变化就需要刷新
//            if (configService.get() != null && listener.get() != null){
//                logger.info("移除旧的监听..."+DATA_ID_OLD + GROUP_OLD);
//                configService.get().removeListener(DATA_ID_OLD,GROUP_OLD,listener.get());
//            }
                creatListener(service);
                //需要刷新client
                build.remove();
                //重新拉取权限配置
                buildFromNacos();
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        return true;
    }


    private void creatListener (ConfigService service) throws NacosException {
        logger.info("添加新nacos侦听器.....");
        Listener temp = new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                logger.info("nacos服务器下发minioPolicy安全配置json数据.....");
                logger.info("data_id,group:{},{}", minioProperties.getDataId(), minioProperties.getGroup());
                logger.info("接收到的数据为:{}", configInfo);
                receivePolicy(configInfo);
            }
        };
        service.addListener(minioProperties.getDataId(), minioProperties.getGroup(), temp);
        configService.set(service);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("minio服务启动，初始化configService.....");
        logger.info("data_id,group:{},{}", minioProperties.getDataId(), minioProperties.getGroup());

        setDATA_ID_OLD(minioProperties.getDataId());
        setGROUP_OLD(minioProperties.getGroup());
        setSERVER_ADDR_OLD(minioProperties.getServerAddr());
        logger.info("data_id_old,group_old:{},{}",DATA_ID_OLD,GROUP_OLD);

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, minioProperties.getServerAddr());
        ConfigService service = NacosFactory.createConfigService(properties);
        creatListener(service);

        logger.info("拉取"+ minioProperties.getDataId()+"配置.....");
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
        logger.info("data_id,group:{},{}", minioProperties.getDataId(), minioProperties.getGroup());
        String nacosConfig = configService.get().getConfig(minioProperties.getDataId(), minioProperties.getGroup(), 5000);
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
    public void setDefaultPolicy(String bucket) throws Exception {
        String config = defaultPolicy.get();
        this.client().setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(config).build());
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
