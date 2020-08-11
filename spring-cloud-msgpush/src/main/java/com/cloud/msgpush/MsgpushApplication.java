package com.cloud.msgpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@EnableDiscoveryClient
public class MsgpushApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgpushApplication.class, args);
    }

}
