package com.cyq.flink.redis;

import com.cyq.flink.redis.client.redisson.RedissonConfigHelper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author chaiyongqiang
 */
public class RedissonContainer implements RedisCommandsContainer, Closeable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RedissonContainer.class);
    private RedissonClient redisson = null;
    private Map<String,String> properties;

    /**
     * Initialize Redis command container for Redis cluster.
     */
    public RedissonContainer(Map<String,String> properties) throws Exception {
        Objects.requireNonNull(properties, "Jedis cluster can not be null");
        this.properties = properties;

    }

    @Override
    public void open() throws Exception {
        Config config = null;
        config = RedissonConfigHelper.createConfig(properties);
        this.redisson = Redisson.create(config);
    }

    @Override
    public void hset(String key, Map<String, String> value, Integer ttl) {

        try {
            redisson.getMap(key).putAll(value);
            if (ttl != null && ttl > 0) {
                redisson.getMap(key).expire((long)ttl, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command HSET to hash of key {} error message {}",
                        key, e.getMessage());
            }
            throw e;
        }
    }

    @Override
    public void delete(String key, RedisCommand t) {
        switch (t){
            case HSET:
                redisson.getMap(key).delete();
                break;
            case SET:
                redisson.getBucket(key).delete();
                break;
            default:
                // Do nothing here. Just make the compiler happy.
        }
    }

    @Override
    public void set(String key, String value,Integer ttl) {
        try {
            redisson.getBucket(key).set(value);
            if (ttl != null && ttl > 0) {
                redisson.getBucket(key).expire((long)ttl, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Cannot send Redis message with command SET to string of key {} error message {}",
                        key, e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Closes the {@link}.
     */
    @Override
    public void close() throws IOException {
        this.redisson.shutdown();
    }

}

