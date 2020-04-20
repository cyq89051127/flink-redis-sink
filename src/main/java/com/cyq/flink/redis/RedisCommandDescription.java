package com.cyq.flink.redis;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author chaiyongqiang
 */
public class RedisCommandDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    private RedisCommand redisCommand;

    private String additionalKey;

    private Integer additionalTTL;

    public RedisCommandDescription(RedisCommand redisCommand, String additionalKey, Integer additionalTTL) {
        Objects.requireNonNull(redisCommand, "Redis command type can not be null");
        this.redisCommand = redisCommand;
        this.additionalKey = additionalKey;
        this.additionalTTL = additionalTTL;
    }

    public RedisCommandDescription(RedisCommand redisCommand, String additionalKey) {
        this(redisCommand, additionalKey, null);
    }

    public RedisCommandDescription(RedisCommand redisCommand, Integer additionalTTL) {
        this(redisCommand, null, additionalTTL);
    }

    public RedisCommandDescription(RedisCommand redisCommand) {
        this(redisCommand, null, null);
    }


    public RedisCommand getCommand() {
        return redisCommand;
    }

    public String getAdditionalKey() { return additionalKey; }

    public Integer getAdditionalTTL() { return additionalTTL; }
}

