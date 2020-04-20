package com.cyq.flink.redis.client.redisson;

/**
 * @author chaiyongqiang
 */
class RedissonConstant {
    static final String REDIS_URL_PERFIX = "redis://";

    static final String REDIS_MODE = "redis.mode";

    static final String REDIS_MODE_DEFAULT = "cluster";

    static final String REDIS_PASSWORD = "redis.mode";

    static final String THREADS = "threads";

    static final String NETTY_THREADS = "netty.threads";

    static final String CODEC = "codec";

    static final String REFERENCE_ENABLE = "reference.enable";

    static final String LOCKWATCHDOG_TIMEOUT = "lockWatchdogTimeout";

    static final String KEEPPUBSUBORDER = "keeppubsuborder";

    static final String DECODE_IN_EXECUTOR = "decode.in.executor";

    static final String USE_SCRIPT_CACHE = "use.script.cache";

    static final String MIN_CLEAN_UP_DELAY = "min.clean.up.delay";

    static final String MAX_CLEAN_UP_DELAY = "max.clean.up.delay";

    static final String SCAN_INTERVAL = "scan.interval";

    static final String SLAVE_CONNECTION_MINiMUM_IDLE_SIZE = "scan.slaveConnectionMinimumIdleSize";

    static final String SLAVE_CONNECTION_POOL_SIZE = "slaveConnectionPoolSize";

    static final String FAILED_SLAVE_CONNECTION_INTERVAL = "failedSlaveReconnectionInterval";

    static final String FAILED_SLAVE_CHECK_INTERVAL = "failedSlaveCheckInterval";

    static final String MASTER_CONNECTION_MINIMUM_IDLE_SIZE = "masterConnectionMinimumIdleSize";

    static final String MASTER_CONNECTION_POOL_SIZE = "masterConnectionPoolSize";

    static final String SUBSCRIPTION_CONNECTION_MINIMUM_IDLE_SIZE = "subscriptionConnectionMinimumIdleSize";

    static final String SUBSCRIPTION_CONNECTION_POOL_SIZE = "subscriptionConnectionPoolSize";

    static final String DNS_MONITORING_INTERVAL = "dnsMonitoringInterval";

    static final String IDLE_CONNECTION_TIMEOUT = "idle.connection.timeout";

    static final String PING_TIMEOUT = "ping.timeout";

    static final String CONNECT_TIMEOUT = "connection.timeout";

    static final String TIMEOUT = "timeout";

    static final String RETRY_ATTEMPTS = "retry.attempts";

    static final String RETRY_INTERVAL = "retry.interval";

    static final String SUBSCRIPTION_PER_CONNECTION = "subscription.per.connection";

    static final String CLIENT_NAME = "client.name";

    static final String SSL_ENABLE_ENDPOINT_IDENTIFICATION = "ssl.enable.endpoint.identification";

    static final String SSL_TRUSTSTORE = "ssl.truststore";

    static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";

    static final String SSL_KEYSTORE = "ssl.keystore";

    static final String SSL_KEYSTORE_PASSWORD = "keystore.password";

    static final String PING_CONNECTION_INTERVAL = "ping.connection.interval";

    static final String KEEP_ALIVE = "keep.alive";

    static final String TCP_NO_DELAY = "tcp.no.delay";
}

