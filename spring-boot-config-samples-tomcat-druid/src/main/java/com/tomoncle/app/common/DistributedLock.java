/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.app.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

/**
 * @author tomoncle
 */
public final class DistributedLock {

    private static final ThreadLocal<String> localCertificates = new ThreadLocal<>();
    private Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    private DistributedLock() {
    }

    public static DistributedLock getInstance() {
        return InnerClass.distributedLock;
    }

    private Jedis jedis() {
        // Jedis jedis = InnerClass.jedisPool.getResource(); // 获取连接
        // jedis.close(); // 释放到连接池
        return InnerClass.jedisPool.getResource();
    }

    /**
     * 阻塞锁，加分布式锁
     *
     * @param key               key
     * @param certificate       加锁对象，凭证
     * @param expireTimeSeconds 超时时间
     * @return Boolean
     */
    public Boolean lock(String key, String certificate, int expireTimeSeconds) {
        try (Jedis jedis = this.jedis()) {
            String value = jedis.set(key, certificate, SetParams.setParams().nx().px(expireTimeSeconds * 1000));
            while (!Objects.equals("OK", value)) {
                value = jedis.set(key, certificate, SetParams.setParams().nx().px(expireTimeSeconds * 1000));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.debug("加锁：[ key：" + key + " ，凭据：" + certificate + " ]: " + value);
            return Objects.equals("OK", value);
        }
    }

    /**
     * 阻塞锁，加分布式锁, 自动维护凭据
     *
     * @param key               key
     * @param expireTimeSeconds 超时时间
     * @return Boolean
     */
    public Boolean lock(String key, int expireTimeSeconds) {
        String certificate = UUID.randomUUID().toString().replaceAll("-", "");
        localCertificates.set(certificate);
        return lock(key, certificate, expireTimeSeconds);
    }

    /**
     * 非阻塞锁，加分布式锁
     *
     * @param key               key
     * @param certificate       加锁对象，凭证
     * @param expireTimeSeconds 超时时间
     * @return Boolean
     */
    public Boolean unBlockLock(String key, String certificate, int expireTimeSeconds) {
        try (Jedis jedis = this.jedis()) {
            String value = jedis.set(key, certificate, SetParams.setParams().nx().px(expireTimeSeconds * 1000));
            logger.debug("加锁：[ key：" + key + " ，凭据：" + certificate + " ]: " + value);
            return Objects.equals("OK", value);
        }
    }

    /**
     * 删除分布式锁
     *
     * @param key         key
     * @param certificate 解锁对象，凭证
     * @return Boolean
     */
    public Boolean unlock(String key, String certificate) {
        try (Jedis jedis = this.jedis()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object value = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(certificate));
            logger.debug("解锁：[ key：" + key + " ，凭据：" + certificate + " ]: " + value);
            return Objects.equals("1", value.toString());
        }
    }

    /**
     * 删除分布式锁, 自动维护凭据
     *
     * @param key key
     * @return Boolean
     */
    public Boolean unlock(String key) {
        String certificate = localCertificates.get();
        localCertificates.remove();
        return unlock(key, certificate);
    }

    private static class InnerClass {
        private static final DistributedLock distributedLock = new DistributedLock();

        private static final JedisPool jedisPool = getJedisPool();

        private static JedisPool getJedisPool() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(10);
            jedisPoolConfig.setMinIdle(10);
            jedisPoolConfig.setMaxTotal(50);
            return new JedisPool(jedisPoolConfig);
        }

    }

}
