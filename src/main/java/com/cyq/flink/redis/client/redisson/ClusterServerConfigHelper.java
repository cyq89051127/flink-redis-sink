package com.cyq.flink.redis.client.redisson;

import com.cyq.flink.redis.descriptor.RedisValidator;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.Map;

/**
 * @author chaiyongqiang
 */
public class ClusterServerConfigHelper {
    public static ClusterServersConfig createClusterServerConfig(Map<String, String> props, Config config) {
        ClusterServersConfig csc = config.useClusterServers();
        csc.addNodeAddress(getNodeList(props.getOrDefault(RedisValidator.REDIS_NODES,null)));
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SCAN_INTERVAL))) {
            csc.setScanInterval(Integer.valueOf(props.get(RedissonConstant.SCAN_INTERVAL)));
        }
        BaseMasterSlaveServersConfigHelper.resloveConfig(props, csc);
        return csc;
    }

    private static String[] getNodeList(String servers) {
        String[] serverArray = StringUtils.split(servers, ",");
        return Arrays.stream(serverArray).map(ClusterServerConfigHelper::toRedisAddress).toArray(x -> new String[serverArray.length]);
    }

    private static String toRedisAddress(String server) {
        return RedissonConstant.REDIS_URL_PERFIX + server;
    }
}
