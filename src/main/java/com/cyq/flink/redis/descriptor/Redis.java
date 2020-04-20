package com.cyq.flink.redis.descriptor;

import org.apache.flink.table.descriptors.ConnectorDescriptor;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.*;

/**
 * @author chaiyongqiang
 */
public class Redis extends ConnectorDescriptor {

    private static final Logger LOG = LoggerFactory.getLogger(Redis.class);

    Map<String, String> properties = new HashMap<>();

    private String mode = null;
    private String redisCommand = null;
    private Integer ttl;

    public Redis(String type, int version, boolean formatNeeded) {
        super(REDIS, version, formatNeeded);
    }

    public Redis() {
        this(REDIS, 1, false);
    }

    /**
     * redis operation type.
     * @param redisCommand redis operation type
     * @return this descriptor.
     */
    public Redis command(String redisCommand) {
        this.redisCommand = redisCommand;
        properties.put(REDIS_COMMAND, redisCommand);
        return this;
    }

    /**
     * ttl for specified key.
     * @param ttl time for key.
     * @returnthis descriptor
     */
    public Redis ttl(Integer ttl) {
        this.ttl = ttl;
        properties.put(REDIS_KEY_TTL, String.valueOf(ttl));
        return this;
    }

    /**
     * redis mode to connect a specified redis cluster
     * @param mode redis mode
     * @return this descriptor
     */
    public Redis mode(String mode) {
        this.mode = mode;
        properties.put(REDIS_MODE, mode);
        return this;
    }

    /**
     * add properties used to connect to redis.
     * @param k specified key
     * @param v value for specified key
     * @return this descriptor
     */
    public Redis property(String k, String v) {
        properties.put(k, v);
        return this;
    }

    @Override
    protected Map<String, String> toConnectorProperties() {
        validate();
        return properties;
    }

    /**
     * validate the necessary properties for redis descriptor.
     */
    public void validate() {
        Preconditions.checkArgument(properties.containsKey(REDIS_COMMAND), "need specified redis command");
        if (! mode.equalsIgnoreCase(REDIS_CLUSTER)) {
            LOG.warn(mode + " NOT SUPPORTED. Using redis cluster mode instead.");
        }
        Preconditions.checkArgument(properties.containsKey(REDIS_NODES), "cluster mode need cluster-nodes info");
    }
}


