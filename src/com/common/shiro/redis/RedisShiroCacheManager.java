package com.common.shiro.redis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.cache.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LiChaoJie
 * @description redis缓存管理器 实现shiro缓存管理器
 * @date 2017-09-28 17:11
 * @modified By
 */
public class RedisShiroCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisShiroCacheManager.class);

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    private RedisManager redisManager;

    private String keyPrefix;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get instance of redisShiroCache by name:" + name);
        Cache c = caches.get(name);
        if (c == null) {
            c = new RedisShiroCache<K, V>(redisManager, keyPrefix);
            caches.put(name, c);
        }
        return c;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

}