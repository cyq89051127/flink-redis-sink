package com.cyq.flink.redis;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.factories.StreamTableSinkFactory;
import org.apache.flink.table.sinks.StreamTableSink;
import org.apache.flink.types.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.*;
import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR;
import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR_TYPE;
import static org.apache.flink.table.descriptors.DescriptorProperties.*;
import static org.apache.flink.table.descriptors.FormatDescriptorValidator.FORMAT;
import static org.apache.flink.table.descriptors.Schema.*;
import static org.apache.flink.table.descriptors.Schema.SCHEMA;

/**
 * @author chaiyongqiang
 */
public class RedisTableSinkFactory implements StreamTableSinkFactory<Tuple2<Boolean, Row>> {

    @Override
    public StreamTableSink<Tuple2<Boolean, Row>> createStreamTableSink(Map<String, String> properties) {
        return new RedisTableSink(properties);
    }

    @Override
    public Map<String, String> requiredContext() {
        Map<String, String> require = new HashMap<>();
        require.put(CONNECTOR_TYPE, REDIS);
        return require;
    }

    @Override
    public List<String> supportedProperties() {
        List<String> properties = new ArrayList<>();
        properties.add(REDIS_MODE);
        properties.add(REDIS_COMMAND);
        properties.add(REDIS_NODES);
        properties.add(REDIS_KEY_TTL);
        properties.add(REDIS_PRIMARY_KEY);
        properties.add(REDIS_TABLE_NAME);
        properties.add(REDIS_VALUE_TYPE);
        // schema
        properties.add(SCHEMA + ".#." + SCHEMA_TYPE);
        properties.add(SCHEMA + ".#." + SCHEMA_DATA_TYPE);
        properties.add(SCHEMA + ".#." + SCHEMA_NAME);
        properties.add(SCHEMA + ".#." + SCHEMA_FROM);
        // format wildcard
        properties.add(FORMAT + ".*");
        properties.add(CONNECTOR + ".*");

        // watermark
        properties.add(SCHEMA + "." + WATERMARK + ".#."  + WATERMARK_ROWTIME);
        properties.add(SCHEMA + "." + WATERMARK + ".#."  + WATERMARK_STRATEGY_EXPR);
        properties.add(SCHEMA + "." + WATERMARK + ".#."  + WATERMARK_STRATEGY_DATA_TYPE);
        return properties;
    }
}
