package com.cloud.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.cloud.*",exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class MinioApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MinioApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
