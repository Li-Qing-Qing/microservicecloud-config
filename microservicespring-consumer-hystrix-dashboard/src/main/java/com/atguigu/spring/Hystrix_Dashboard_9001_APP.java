package com.atguigu.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class Hystrix_Dashboard_9001_APP {
    public static void main(String[] args) {
        SpringApplication.run(Hystrix_Dashboard_9001_APP.class);
    }
}
