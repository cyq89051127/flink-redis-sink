package com.cyq.flink.redis;

/**
 * @author chaiyongqiang
 */
/**
 * All available data type for Redis.
 */
public enum RedisDataType {

    /**
     * Strings are the most basic kind of Redis value. Redis Strings are binary safe,
     * this means that a Redis string can contain any kind of data, for instance a JPEG image or a serialized Ruby object.
     * A String value can be at max 512 Megabytes in length.
     */
    STRING,

    /**
     * Redis Hashes are maps between string fields and string values.
     */
    HASH
}