package com.emard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LearninSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearninSpringBootApplication.class, args);
    }
}
