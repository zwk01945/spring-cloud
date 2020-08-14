package com.cloud.springcloudslaveservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@MapperScan(basePackages = "com.cloud.springcloudslaveservice.mapper")
public class SpringCloudSlaveserviceApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringCloudSlaveserviceApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
