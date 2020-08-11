package com.cloud.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@EnableDiscoveryClient
public class MinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }

}
