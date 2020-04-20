package com.cyq.flink.redis;

import java.util.HashMap;
import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS;
import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR;

/**
 * @author chaiyongqiang
 */
public class RedisCommandsContainerBuilder {

    public static RedisCommandsContainer build(Map<String,String> properties) throws Exception {
        Map<String,String> redisProperties = new HashMap<>();
        properties.forEach((key,value) -> {
            if (key.startsWith(REDIS)){
                redisProperties.put(key,value);
            } else if (key.startsWith(CONNECTOR)){
                redisProperties.put(key.substring(CONNECTOR.length() + 1),value);
            }
        });

        return new RedissonContainer(redisProperties);
    }
}
