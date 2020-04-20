package com.cyq.flink.redis.util;

/**
 * @author chaiyongqiang
 */
public class Util {
    public static void checkArgument(boolean condition, String message) {
        if(!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
