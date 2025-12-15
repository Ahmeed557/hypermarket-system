package com.hypermarket.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// ده الـ Main Class بتاع API Gateway
@SpringBootApplication
@EnableDiscoveryClient  // ← عشان يسجل نفسه في Eureka
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}