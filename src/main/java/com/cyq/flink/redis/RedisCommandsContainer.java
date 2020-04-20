package com.cyq.flink.redis;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author chaiyongqiang
 */
public interface RedisCommandsContainer extends Serializable {

    /**
     * Open the Jedis container.
     *
     * @throws Exception if the instance can not be opened properly
     */
    void open() throws Exception;

    /**
     * Sets field in the hash stored at key to value, with TTL, if needed.
     * Setting expire time to key is optional.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists in the hash, it is overwritten.
     *
     * @param key Hash name
     * @param value Map value
     * @param ttl Hash expire time
     */
    void hset(String key, Map<String,String> value, Integer ttl);


    /**
     * @param key the key we want to delete
     * @param t the type of the key we want to delete
     */
    void delete(String key, RedisCommand t);

    void set(String key, String value, Integer ttl);

//    void set(String key, Object value, RedisCommand redisCommand, Integer ttl);

    /**
     * Close the Jedis container.
     *
     * @throws IOException if the instance can not be closed properly
     */
    void close() throws IOException;
}

