package com.common.shiro.redis;

import com.cache.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SerializationUtils;

import java.util.*;


/**
 * @author LiChaoJie
 * @description redis缓存 实现shiro缓存
 * @date 2017-09-28 17:11
 * @modified By
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(RedisShiroCache.class);

    private String keyPrefix = "shiro_redis_cache:";

    private RedisManager redisManager;

    public RedisShiroCache(RedisManager redisManager, String keyPrefix) {
        if (redisManager == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.redisManager = redisManager;
        this.keyPrefix = keyPrefix;
    }

    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        } else if (key instanceof PrincipalCollection) {
            String preKey = this.keyPrefix + key.toString();
            return preKey.getBytes();
        } else {
            return SerializationUtils.serialize(key);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        logger.debug("get object form redis by key: key = " + key);
        try {
            if (key == null) {
                return null;
            } else {
                byte[] rawValue = redisManager.get(getByteKey(key));
                return (V) SerializationUtils.deserialize(rawValue);
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }

    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("put key and value into redis: key = " + key);
        try {
            redisManager.set(getByteKey(key), SerializationUtils.serialize(value));
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("drop key in redis: key = " + key);
        try {
            V previous = get(key);
            redisManager.del(getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("drop all of redis");
        try {
            redisManager.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        try {
            Long longSize = redisManager.dbSize();
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        try {
            Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<K>();
                for (byte[] key : keys) {
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values() {
        try {
            Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<>(keys.size());
                for (byte[] key : keys) {
                    V value = get((K) key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

}