package com.cyq.flink.redis;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Row;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS_COMMAND;

/**
 * @author chaiyongqiang
 */
public abstract class RowRedisMapper implements RedisMapper<Tuple2<Boolean, Row>>, RedisMapperHandler {

    private Integer ttl;

    private RedisCommand redisCommand;

    public RowRedisMapper(RedisCommand redisCommand,Integer ttl) {
        this.ttl = ttl;
        this.redisCommand = redisCommand;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public RedisCommand getRedisCommand() {
        return redisCommand;
    }

    public void setRedisCommand(RedisCommand redisCommand) {
        this.redisCommand = redisCommand;
    }

    public RowRedisMapper() {
    }

    public RowRedisMapper(int ttl, RedisCommand redisCommand) {
        this.ttl = ttl;
        this.redisCommand = redisCommand;
    }

    public RowRedisMapper(RedisCommand redisCommand) {
        this.redisCommand = redisCommand;
    }

    @Override
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(redisCommand,ttl);
    }

    @Override
    public String getKeyFromData(Tuple2<Boolean, Row> data) {
        return data.f1.getField(0).toString();
    }


    //    @Override
    public Map<String, String> requiredContext() {
        Map<String, String> require = new HashMap<>();
        require.put(REDIS_COMMAND, getRedisCommand().name());
        return require;
    }

    @Override
    public boolean equals(Object obj) {
        RedisCommand redisCommand = ((RowRedisMapper) obj).redisCommand;
        return this.redisCommand == redisCommand;
    }

    @Override
    public Optional<Integer> getAdditionalTTL(Tuple2<Boolean, Row> data) {
        return Optional.ofNullable(getTtl());
    }
}


