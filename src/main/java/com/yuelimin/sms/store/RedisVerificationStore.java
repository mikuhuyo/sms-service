package com.yuelimin.sms.store;

import com.yuelimin.common.cache.IRedisCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 持久化签名至redis
 *
 * @author yuelimin
 */
@Component
public class RedisVerificationStore {
    @Resource
    private IRedisCache redisCache;

    /**
     * 设置键值对以及过期时间
     */
    public void set(String key, String value, Integer expire) {
        redisCache.set(key, value, expire);
    }

    /**
     * 获取key对应的value
     */
    public String get(String key) {
        return redisCache.get(key);
    }
}
