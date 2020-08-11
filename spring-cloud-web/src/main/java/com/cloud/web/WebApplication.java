package com.cloud.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.cloud.*")
@EnableDiscoveryClient
@EnableFeignClients
public class WebApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(WebApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
