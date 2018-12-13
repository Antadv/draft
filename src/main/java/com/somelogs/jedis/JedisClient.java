package com.somelogs.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 描述
 *
 * @author LBG - 2018/12/6 0006
 */
public class JedisClient {

    private static final String REDIS_HOST = "192.168.18.128";
    private static final Integer REDIS_PORT = 6379;

    public static void main(String[] args) {
        pool();
    }

    private static void direct() {
        Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        jedis.set("hello", "world");
        System.out.println(jedis.get("hello"));
    }

    private static void pool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 设置最大连接数为默认值的5倍
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        // 设置最大空闲连接数为默认的3倍
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        // 设置最小空闲连接数为默认值的2倍
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
        // 设置开启jmx功能
        poolConfig.setJmxEnabled(true);
        // 设置连接池没有接连后客户端最大等待时间
        poolConfig.setMaxWaitMillis(3000);

        JedisPool jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
        try (Jedis jedis = jedisPool.getResource()) {
            System.out.println(jedis.get("hello"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
