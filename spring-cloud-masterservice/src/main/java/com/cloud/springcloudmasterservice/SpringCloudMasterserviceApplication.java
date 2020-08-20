package com.cloud.springcloudmasterservice;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@MapperScan(basePackages = "com.cloud.springcloudmasterservice.mapper")
@EnableFeignClients
public class SpringCloudMasterserviceApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringCloudMasterserviceApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
