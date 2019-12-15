package com.renchao.server;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.renchao.server.model.Metrics;
import com.renchao.server.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubboConfiguration
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
