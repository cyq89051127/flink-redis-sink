package com.cyq.flink.redis.client.redisson;

import com.cyq.flink.redis.descriptor.RedisValidator;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author chaiyongqiang
 */
public class RedissonConfigHelper {

    private static Logger logger = LoggerFactory.getLogger(RedissonConfigHelper.class);

    public static Config createConfig(Map<String, String> props) throws Exception {
        Config config = new Config();
        resloveConfig(props, config);
        return config;
    }

    /***
     * This method helps resloving config with custom properties.
     *
     * Note : eventLoopGroup,transportMode,executor are not supported to custom.
     * @param props : custom properties
     * @param config
     */
    private static void resloveConfig(Map<String, String> props, Config config) throws Exception {
        // set codec
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.CODEC))) {
            try {
                config.setCodec((Codec) Class.forName(props.get(RedissonConstant.CODEC).toString()).newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.error("Something wrong happens when initializing codec. Ignoring it. Exception : ", e);
            }
        } else {
            config.setCodec(new org.redisson.client.codec.StringCodec());
        }

        // set threads
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.THREADS))) {
            config.setThreads(Integer.parseInt(props.get(RedissonConstant.THREADS)));
        }

        // set netty threads
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.NETTY_THREADS))) {
            config.setNettyThreads(Integer.parseInt(props.get(RedissonConstant.NETTY_THREADS)));
        }

        // set referenceEnabled
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.REFERENCE_ENABLE))) {
            config.setReferenceEnabled(Boolean.valueOf(props.get(RedissonConstant.REFERENCE_ENABLE)));
        }

        // set lockWatchdogTimeout
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.LOCKWATCHDOG_TIMEOUT))) {
            config.setLockWatchdogTimeout(Integer.parseInt(props.get(RedissonConstant.LOCKWATCHDOG_TIMEOUT)));
        }

        // set keepPubSubOrder
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.KEEPPUBSUBORDER))) {
            config.setReferenceEnabled(Boolean.valueOf(props.get(RedissonConstant.KEEPPUBSUBORDER)));
        }

        // set decodeInExecutor
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.DECODE_IN_EXECUTOR))) {
            config.setDecodeInExecutor(Boolean.valueOf(props.get(RedissonConstant.DECODE_IN_EXECUTOR)));
        }

        // set useScriptCache
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.USE_SCRIPT_CACHE))) {
            config.setUseScriptCache(Boolean.valueOf(props.get(RedissonConstant.USE_SCRIPT_CACHE)));
        }

        // set minCleanUpDelay
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.MIN_CLEAN_UP_DELAY))) {
            config.setMinCleanUpDelay(Integer.parseInt(props.get(RedissonConstant.MIN_CLEAN_UP_DELAY)));
        }

        //set maxCleanUpDelay
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.MAX_CLEAN_UP_DELAY))) {
            config.setMinCleanUpDelay(Integer.parseInt(props.get(RedissonConstant.MAX_CLEAN_UP_DELAY)));
        }

        String redisDeployMode = props.getOrDefault(RedissonConstant.REDIS_MODE, RedissonConstant.REDIS_MODE_DEFAULT).toUpperCase();
        switch (RedisMode.valueOf(redisDeployMode.toUpperCase())) {
            case CLUSTER:
                if (StringUtil.isEmpty(props.get(RedisValidator.REDIS_NODES))) {
                    throw new Exception("Redis Server can't be null");
                }
                ClusterServerConfigHelper.createClusterServerConfig(props, config);

                break;
            default:
                logger.error("Not Supported redis mode.");
                break;
        }
    }
}
