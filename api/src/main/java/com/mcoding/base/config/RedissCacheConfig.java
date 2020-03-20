package com.mcoding.base.config;

import com.google.common.collect.Maps;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wzt on 2020/3/19.
 * @version 1.0
 */
@Configuration
public class RedissCacheConfig {

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = Maps.newHashMap();
        config.put("CACHE_ENTERPRISE", new CacheConfig(24 * 60 * 60 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
