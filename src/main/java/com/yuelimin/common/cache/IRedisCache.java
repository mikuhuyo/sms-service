package com.yuelimin.common.cache;

import java.util.Set;

/**
 * @author yuelimin
 */
public interface IRedisCache {
    /**
     * 列出所有的key
     */
    Set<String> getKeys();

    /**
     * 通过正则获取key
     */
    Set<String> getKeys(String pattern);

    /**
     * 检查给定key是否存在
     */
    Boolean exists(String key);

    /**
     * 移除给定的一个或多个key; 如果key不存在, 则忽略该命令
     */
    void del(String key);

    /**
     * 简单的字符串设置
     */
    void set(String key, String value);

    /**
     * 设置字符串过期时间
     */
    void set(String key, String value, Integer expiration);

    /**
     * 返回key所关联的字符串值
     */
    String get(String key);

    /**
     * 为给定key设置生存时间; 当key过期时, 它会被自动删除
     */
    void expire(String key, int expire);

    /**
     * 如果key已经存在并且是一个字符串, APPEND命令将value追加到key原来的值之后
     */
    void append(String key, String value);

    /**
     * 获取旧值返回新值, 不存在返回nil
     */
    String getset(String key, String newValue);

    /**
     * 分布锁
     */
    boolean setnx(String key, String value);

    /**
     * 计数器
     */
    Long incrBy(String key, Long delta);
}
