package com.cyq.flink.redis;

/**
 * @author chaiyongqiang
 */
/**
 * All available commands for Redis. Each command belongs to a {@link RedisDataType} group.
 */
public enum RedisCommand {

    /**
     * Set key to hold the string value. If key already holds a value,
     * it is overwritten, regardless of its type.
     */
    SET(RedisDataType.STRING),

    /**
     * Sets field in the hash stored at key to value. If key does not exist,
     * a new key holding a hash is created. If field already exists in the hash, it is overwritten.
     */
    HSET(RedisDataType.HASH);

    /**
     * The {@link RedisDataType} this command belongs to.
     */
    private RedisDataType redisDataType;

    RedisCommand(RedisDataType redisDataType) {
        this.redisDataType = redisDataType;
    }


    /**
     * The {@link RedisDataType} this command belongs to.
     * @return the {@link RedisDataType}
     */
    public RedisDataType getRedisDataType(){
        return redisDataType;
    }
}

