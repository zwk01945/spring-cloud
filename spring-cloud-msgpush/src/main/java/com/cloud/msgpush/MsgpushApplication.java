package com.cloud.msgpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.cloud.*",exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class MsgpushApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MsgpushApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
