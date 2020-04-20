package com.cyq.flink.redis;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * @author chaiyongqiang
 */
public class KafkaToRedisSql {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(streamExecutionEnvironment, bsSettings);

        String createSourceTable = "CREATE TABLE source (\n" +
                "    id  STRING,\n" +
                "    addr STRING,\n" +
                "    event_ts BIGINT\n" +
                ") WITH (\n" +
                "  'connector.type' = 'kafka',       \n" +
                "  'connector.version' = 'universal',\n" +
                "  'connector.topic' = 'flink_in_in',\n" +
                "  'connector.startup-mode' = 'earliest-offset',\n" +
                "  'connector.properties.zookeeper.connect' = '10.1.236.35:2181',\n" +
                "  'connector.properties.bootstrap.servers' = '10.1.236.67:9092',\n" +
                "  'connector.properties.group.id' = 'testGroup',\n" +
                "  'connector.startup-mode' = 'earliest-offset',\n" +
                "  'format.ignore-parse-errors' = 'true',\n" +
                "  'format.type'='csv',\n" +
                "  'update-mode' = 'append'\n" +
                ")";


        String createSinkTable = "CREATE TABLE sink (\n" +
                "    id  STRING,\n" +
                "    addr STRING,\n" +
                "    event_ts TIMESTAMP(3),\n" +
                "    WATERMARK FOR event_ts AS event_ts - INTERVAL '5' SECOND\n" +
                ") WITH (\n" +
                "  'connector.type' = 'redis',       \n" +
                "  'redis.table.name' = 'out_redis',\n" +
                "  'redis-nodes' = 'localhost:6379\n" +
                "  'redis.primary-key' = 'id,addr',\n" +
                "  'redis.value-type' = 'HASH'\n" +
                ")\n";


        String exeCommand = "insert into sink select id, addr, cast(event_ts as timestamp(3)) from source where id <> 'foo'";


        streamTableEnvironment.sqlUpdate(createSourceTable);

        streamTableEnvironment.sqlUpdate(createSinkTable);

        String[] retTables = streamTableEnvironment.listTables();

        for (int i = 0; i < retTables.length; i++) {
            System.out.println(retTables[i]);
        }

        streamTableEnvironment.sqlUpdate(exeCommand);

        streamExecutionEnvironment.execute("KafkaToRedis");

    }
}
