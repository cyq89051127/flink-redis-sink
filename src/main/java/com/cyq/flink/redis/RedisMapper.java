package com.cyq.flink.redis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import org.apache.flink.api.common.functions.Function;

/**
 * @author chaiyongqiang
 */
public interface RedisMapper<T> extends Function, Serializable {

    /**
     * Returns descriptor which defines data type.
     *
     * @return data type descriptor
     */
    RedisCommandDescription getCommandDescription();

    /**
     * Extracts key from data.
     *
     * @param data source data
     * @return key
     */
    String getKeyFromData(T data);

    /**
     * Extracts value from data.
     *
     * @param data source data
     * @return value
     */
    Map<String,String> getValueAsMap(T data);

    String getValueAsJson(T data, ObjectMapper objectMapper) throws Exception;

    /**
     * Extracts the additional key from data as an {@link Optional <String>}.
     * The default implementation returns an empty Optional.
     *
     * @param data
     * @return Optional
     */
    default Optional<String> getAdditionalKey(T data) {
        return Optional.empty();
    }

    /**
     * Extracts the additional time to live (TTL) for data as an {@link Optional<Integer>}.
     * The default implementation returns an empty Optional.
     *
     * @param data
     * @return Optional
     */
    default Optional<Integer> getAdditionalTTL(T data) {
        return Optional.empty();
    }
}


