package com.cyq.flink.redis;

import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS_VALUE_TYPE;

/**
 * @author chaiyongqiang
 */
public class RedisMapperFactory {
    static RowRedisMapper getRedisMapper(Map<String, String> properties, RedisTableSchema redisTableSchema) {
        RedisDataType sinkType = RedisDataType.valueOf(properties.getOrDefault(REDIS_VALUE_TYPE,"HASH".toUpperCase()));
        switch (sinkType){
            case HASH:
                return new HSetMapper(properties,redisTableSchema);
            case STRING:
                return new SetMapper(properties,redisTableSchema);
            default:
                return null;
        }
    }
}
