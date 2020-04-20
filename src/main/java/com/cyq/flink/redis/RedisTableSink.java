package com.cyq.flink.redis;

import com.cyq.flink.redis.util.Util;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.table.sinks.UpsertStreamTableSink;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.utils.TableConnectorUtils;
import org.apache.flink.types.Row;
import org.apache.flink.util.Preconditions;

import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS_PRIMARY_KEY;
import static org.apache.flink.table.descriptors.Schema.SCHEMA;

/**
 * @author chaiyongqiang
 */
public class RedisTableSink implements UpsertStreamTableSink<Row> {

    private RowRedisMapper redisMapper;
    private TableSchema tableSchema;
    private RedisTableSchema redisTableSchema;
    private String[] keyFields;
    private boolean isAppendOnly;
    private Map<String, String> properties = null;


    public RedisTableSink(Map<String, String> properties) {
        this.properties = properties;
        Preconditions.checkNotNull(properties, "properties should not be null");
//        flinkJedisConfigBase = new FlinkJedisClusterConfigHandler()
//                .createFlinkJedisConfig(properties);
        final DescriptorProperties descriptorProperties = new DescriptorProperties(true);
        descriptorProperties.putProperties(properties);
        tableSchema = descriptorProperties.getTableSchema(SCHEMA);
        redisTableSchema = createRedisSchema(tableSchema,properties);
        redisMapper = RedisMapperFactory.getRedisMapper(properties,redisTableSchema);// new HSetMapper(properties,redisTableSchema);
    }

    private RedisTableSchema createRedisSchema(TableSchema tableSchema,Map<String,String> properties) {
        RedisTableSchema redisTableSchema = new RedisTableSchema();
        Util.checkArgument(properties.get(REDIS_PRIMARY_KEY) != null, "A primary key must be defined when sinking to redis");
        String[] keyFields = properties.get(REDIS_PRIMARY_KEY).split(",");
        Integer[] keyIndexs = new Integer[keyFields.length];
        DataType[] keyTypes = new DataType[keyFields.length];

        String [] fieldNames = tableSchema.getFieldNames();
        DataType[] fieldTypes = tableSchema.getFieldDataTypes();

        redisTableSchema.setFieldNames(fieldNames);
        redisTableSchema.setFieldTypes(fieldTypes);

        for (int i = 0; i < keyFields.length; i++) {
            for (int j = 0; j < fieldNames.length; j++) {
                if (fieldNames[j].equalsIgnoreCase(keyFields[i])){
                    keyIndexs[i] = j;
                    keyTypes[i] = fieldTypes[j];
                }
            }
        }
        redisTableSchema.initializePrimaryKey(keyFields,keyIndexs,keyTypes);
        return  redisTableSchema;
    }

    @Override
    public DataStreamSink<?> consumeDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        return dataStream.addSink(new RedisSink(properties, redisMapper))
                .setParallelism(dataStream.getParallelism())
                .name(TableConnectorUtils.generateRuntimeName(this.getClass(), getFieldNames()));
    }


    @Override
    public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        consumeDataStream(dataStream);
    }

    @Override
    public TableSink configure(String[] fieldNames, TypeInformation[] fieldTypes) {
        return new RedisTableSink(getProperties());
    }

    @Override
    public TableSchema getTableSchema() {
        return tableSchema;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public void setKeyFields(String[] keys) {
        this.keyFields = keys;
    }

    @Override
    public void setIsAppendOnly(Boolean isAppendOnly) {
        this.isAppendOnly = isAppendOnly;
    }

    @Override
    public TypeInformation<Row> getRecordType() {
        return tableSchema.toRowType();
    }
}


