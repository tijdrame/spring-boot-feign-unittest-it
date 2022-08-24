package com.emard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@Configuration
@Component
@ApplicationPath("/rest")
public class RestEasyConfig extends Application {
//public class RestEasyConfig extends ResourceConfig {
    //public RestEasyConfig() {
      //  register(UserResourceRestEasy.class);
    //}
}