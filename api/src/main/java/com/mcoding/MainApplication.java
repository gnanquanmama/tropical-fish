package com.mcoding;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableRedissonHttpSession
@EnableCaching
@EnableDubbo(scanBasePackages = "com.mcoding")
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.mcoding"})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
