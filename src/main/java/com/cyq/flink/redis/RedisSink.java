package com.cyq.flink.redis;

import com.cyq.flink.redis.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author chaiyongqiang
 */

public class RedisSink<IN> extends RichSinkFunction<IN> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RedisSink.class);

    private Integer additionalTTL;

    private RedisMapper<IN> redisSinkMapper;
    private RedisCommand redisCommand;
    private Map<String,String> properties;
    private ObjectMapper objectMapper;
    private RedisCommandsContainer redisCommandsContainer;

    /**
     * Creates a new {@link RedisSink} that connects to the Redis server.
     *
     * @param redisSinkMapper This is used to generate Redis command and key value from incoming elements.
     */
    public RedisSink(Map<String,String> properties, RedisMapper<IN> redisSinkMapper) {
        Objects.requireNonNull(properties, "Redis connection pool config should not be null");
        Objects.requireNonNull(redisSinkMapper, "Redis Mapper can not be null");
        Objects.requireNonNull(redisSinkMapper.getCommandDescription(), "Redis Mapper data type description can not be null");

        this.properties = properties;
        this.redisSinkMapper = redisSinkMapper;
        this.objectMapper = JsonUtil.getObjectMapper();

        RedisCommandDescription redisCommandDescription = redisSinkMapper.getCommandDescription();

        this.redisCommand = redisCommandDescription.getCommand();
        this.additionalTTL = redisCommandDescription.getAdditionalTTL();
    }

    @Override
    public void invoke(IN input, Context context) throws Exception {
        String key = redisSinkMapper.getKeyFromData(input);
        Optional<Integer> optAdditionalTTL = redisSinkMapper.getAdditionalTTL(input);
        switch (redisCommand) {
            case HSET:
                Map<String,String > mapValue = redisSinkMapper.getValueAsMap(input);
                if (mapValue ==null || mapValue.isEmpty()){
                    redisCommandsContainer.delete(key,redisCommand);
                }else {
                    this.redisCommandsContainer.hset(key, mapValue, optAdditionalTTL.orElse(this.additionalTTL));
                }
                break;
            case SET:
                String jsonValue = redisSinkMapper.getValueAsJson(input,objectMapper);
                if (StringUtils.isEmpty(jsonValue)){
                    redisCommandsContainer.delete(key,redisCommand);
                }else {
                    this.redisCommandsContainer.set(key,jsonValue,optAdditionalTTL.orElse(this.additionalTTL));
                }
                break;
            default:
                throw new Exception("Not support command " + redisCommand.toString());
        }

    }

    /**
     * Initializes the connection to Redis by either cluster or sentinels or single server.
     *
     * @throws IllegalArgumentException if jedisPoolConfig, jedisClusterConfig and jedisSentinelConfig are all null
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        try {
            this.redisCommandsContainer = RedisCommandsContainerBuilder.build(properties);
            this.redisCommandsContainer.open();
        } catch (Exception e) {
            LOG.error("Redis has not been properly initialized: ", e);
            throw e;
        }
    }

    /**
     * Closes commands container.
     * @throws IOException if command container is unable to close.
     */
    @Override
    public void close() throws IOException {
        if (redisCommandsContainer != null) {
            redisCommandsContainer.close();
        }
    }
}

