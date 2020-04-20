package com.cyq.flink.redis.client.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.config.BaseMasterSlaveServersConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author chaiyongqiang
 */
public class BaseMasterSlaveServersConfigHelper {
    public static void resloveConfig(Map<String, String> props, BaseMasterSlaveServersConfig bmsc) {

        // Resolve BaseMasterSlaveServersConfig
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SLAVE_CONNECTION_MINiMUM_IDLE_SIZE))) {
            bmsc.setSlaveConnectionMinimumIdleSize(Integer.valueOf(props.get(RedissonConstant.SLAVE_CONNECTION_MINiMUM_IDLE_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SLAVE_CONNECTION_POOL_SIZE))) {
            bmsc.setSlaveConnectionPoolSize(Integer.valueOf(props.get(RedissonConstant.SLAVE_CONNECTION_POOL_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.FAILED_SLAVE_CONNECTION_INTERVAL))) {
            bmsc.setFailedSlaveCheckInterval(Integer.valueOf(props.get(RedissonConstant.FAILED_SLAVE_CONNECTION_INTERVAL)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.FAILED_SLAVE_CHECK_INTERVAL))) {
            bmsc.setFailedSlaveCheckInterval(Integer.valueOf(props.get(RedissonConstant.FAILED_SLAVE_CHECK_INTERVAL)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.MASTER_CONNECTION_MINIMUM_IDLE_SIZE))) {
            bmsc.setMasterConnectionMinimumIdleSize(Integer.valueOf(props.get(RedissonConstant.MASTER_CONNECTION_MINIMUM_IDLE_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.MASTER_CONNECTION_POOL_SIZE))) {
            bmsc.setMasterConnectionPoolSize(Integer.valueOf(props.get(RedissonConstant.MASTER_CONNECTION_POOL_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SUBSCRIPTION_CONNECTION_MINIMUM_IDLE_SIZE))) {
            bmsc.setSubscriptionConnectionMinimumIdleSize(Integer.valueOf(props.get(RedissonConstant.SUBSCRIPTION_CONNECTION_MINIMUM_IDLE_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SUBSCRIPTION_CONNECTION_POOL_SIZE))) {
            bmsc.setSubscriptionConnectionPoolSize(Integer.valueOf(props.get(RedissonConstant.SUBSCRIPTION_CONNECTION_POOL_SIZE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.DNS_MONITORING_INTERVAL))) {
            bmsc.setDnsMonitoringInterval(Integer.valueOf(props.get(RedissonConstant.DNS_MONITORING_INTERVAL)));
        }


        // Resolve the BaseConfig

        if (StringUtils.isNotEmpty(props.get(RedissonConstant.IDLE_CONNECTION_TIMEOUT))) {
            bmsc.setSlaveConnectionMinimumIdleSize(Integer.valueOf(props.get(RedissonConstant.DNS_MONITORING_INTERVAL)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.PING_TIMEOUT))) {
            bmsc.setPingTimeout(Integer.valueOf(props.get(RedissonConstant.PING_TIMEOUT)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.CONNECT_TIMEOUT))) {
            bmsc.setConnectTimeout(Integer.valueOf(props.get(RedissonConstant.CONNECT_TIMEOUT)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.TIMEOUT))) {
            bmsc.setTimeout(Integer.valueOf(props.get(RedissonConstant.TIMEOUT)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.RETRY_ATTEMPTS))) {
            bmsc.setRetryAttempts(Integer.valueOf(props.get(RedissonConstant.RETRY_ATTEMPTS)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.RETRY_INTERVAL))) {
            bmsc.setRetryInterval(Integer.valueOf(props.get(RedissonConstant.RETRY_INTERVAL)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.REDIS_PASSWORD))) {
            bmsc.setPassword(props.get(RedissonConstant.REDIS_PASSWORD));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SUBSCRIPTION_PER_CONNECTION))) {
            bmsc.setSubscriptionsPerConnection(Integer.valueOf(props.get(RedissonConstant.SUBSCRIPTION_PER_CONNECTION)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.CLIENT_NAME))) {
            bmsc.setClientName(props.get(RedissonConstant.CLIENT_NAME));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SSL_ENABLE_ENDPOINT_IDENTIFICATION))) {
            bmsc.setSslEnableEndpointIdentification(Boolean.parseBoolean(props.get(RedissonConstant.SSL_ENABLE_ENDPOINT_IDENTIFICATION)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SSL_TRUSTSTORE))) {
            try {
                bmsc.setSslTruststore(new URI(props.get(RedissonConstant.SSL_TRUSTSTORE)));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SSL_TRUSTSTORE_PASSWORD))) {
            bmsc.setSslTruststorePassword(props.get(RedissonConstant.SSL_TRUSTSTORE_PASSWORD));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SSL_KEYSTORE))) {
            try {
                bmsc.setSslKeystore(new URI(props.get(RedissonConstant.SSL_KEYSTORE)));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.SSL_KEYSTORE_PASSWORD))) {
            bmsc.setSslKeystorePassword(props.get(RedissonConstant.SSL_KEYSTORE_PASSWORD));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.PING_CONNECTION_INTERVAL))) {
            bmsc.setPingConnectionInterval(Integer.valueOf(props.get(RedissonConstant.PING_CONNECTION_INTERVAL)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.KEEP_ALIVE))) {
            bmsc.setKeepAlive(Boolean.parseBoolean(props.get(RedissonConstant.KEEP_ALIVE)));
        }
        if (StringUtils.isNotEmpty(props.get(RedissonConstant.TCP_NO_DELAY))) {
            bmsc.setTcpNoDelay(Boolean.parseBoolean(props.get(RedissonConstant.TCP_NO_DELAY)));
        }

    }
}
