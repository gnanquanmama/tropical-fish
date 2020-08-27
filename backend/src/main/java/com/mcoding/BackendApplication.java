package com.mcoding;

import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableRedissonHttpSession
@EnableCaching
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.mcoding"})
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
