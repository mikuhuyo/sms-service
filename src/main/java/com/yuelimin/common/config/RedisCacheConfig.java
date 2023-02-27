package com.yuelimin.common.config;

import com.yuelimin.common.cache.RedisCacheImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author yuelimin
 */
@Configuration
public class RedisCacheConfig {
    @Bean
    public RedisCacheImpl redisCache(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheImpl(stringRedisTemplate);
    }
}
