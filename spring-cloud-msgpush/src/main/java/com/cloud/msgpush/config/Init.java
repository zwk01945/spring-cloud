package com.cloud.msgpush.config;

import com.cloud.msgpush.bean.SmsEnv;
import com.cloud.msgpush.socket.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : cloud             *
 *                                                            *
 *         File Name : Init.java                           *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/8/11 9:30                       *
 *                                                            *
 *         Last Update : 2020/8/11 9:30                      *
 *                                                            *
 *------------------------------------------------------------*
 * Functions:                                                 *
 *   初始化系统参数                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
@Component
public class Init implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Init.class);
    SmsEnv smsEnv;

    Integer port;
    @Value("${server.netty.port}")
    public void setPort(Integer port) {
        this.port = port;
    }

    @Autowired
    public void setSmsEnv(SmsEnv smsEnv) {
        this.smsEnv = smsEnv;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("推送服务启动==========");
        logger.info("阿里云短信配置:{},{},{},{},{}",smsEnv.getAccessKeyId(),smsEnv.getAccessKeySecret(),smsEnv.getRegionName()
        ,smsEnv.getSignName(),smsEnv.getTemplateCode());
        logger.info("阿里云短信配置完成===========");
        logger.info("启动websocket服务============");
        new NettyServer(port).start();
    }
}
