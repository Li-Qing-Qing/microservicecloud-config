package com.atguigu.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulGateway_9527_APP {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGateway_9527_APP.class);
    }
}
