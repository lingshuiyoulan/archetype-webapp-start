package com.cache.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author LiChaoJie
 * @description 简单的redis缓存操作 更多操作请直接获取jedis对象
 * @date 2017-09-28 17:11
 * @modified By
 */
public class RedisManager {

    private int expire = 0;

    private JedisPool pool;

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public Jedis getJedis() {
//        return jedisConnectionFactory.getConnection().getNativeConnection();
        return pool.getResource();
    }

    public byte[] get(byte[] key) {
        byte[] value;
        Jedis jedis = getJedis();
        value = jedis.get(key);
        jedis.close();
        return value;
    }

    public String get(String key) {
        String value;
        Jedis jedis = getJedis();
        value = jedis.get(key);
        jedis.close();
        return value;
    }

    public void set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        if (this.expire != 0) {
            jedis.expire(key, this.expire);
        }
        jedis.close();
    }

    public void set(byte[] key, byte[] value, int expire) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        if (expire != 0) {
            jedis.expire(key, expire);
        }
        jedis.close();
    }

    public void set(String key, String value) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        if (this.expire != 0) {
            jedis.expire(key, this.expire);
        }
        jedis.close();
    }

    public void set(String key, String value, int expire) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        if (expire != 0) {
            jedis.expire(key, expire);
        }
        jedis.close();
    }

    public void del(byte[] key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
    }

    public void del(String key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
    }

    public void flushDB() {
        Jedis jedis = getJedis();
        jedis.flushDB();
        jedis.close();
    }

    public Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = getJedis();
        dbSize = jedis.dbSize();
        jedis.close();
        return dbSize;
    }

    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys;
        Jedis jedis = getJedis();
        keys = jedis.keys(pattern.getBytes());
        jedis.close();
        return keys;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}