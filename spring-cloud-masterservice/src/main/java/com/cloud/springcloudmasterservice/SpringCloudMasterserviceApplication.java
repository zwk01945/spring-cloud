package com.cloud.springcloudmasterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@MapperScan(basePackages = "com.cloud.springcloudmasterservice.mapper")
public class SpringCloudMasterserviceApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringCloudMasterserviceApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
