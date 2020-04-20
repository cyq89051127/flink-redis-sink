package com.cyq.flink.redis;

import com.cyq.flink.redis.descriptor.RedisValidator;
import com.cyq.flink.redis.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Row;

import java.util.HashMap;
import java.util.Map;

import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS_DELIMITER;
import static com.cyq.flink.redis.descriptor.RedisValidator.REDIS_TABLE_NAME;


/**
 * @author chaiyongqiang
 */
public class SetMapper extends RowRedisMapper {

    String tableName;
    String delimiter;
    RedisTableSchema redisTableSchema;

    public SetMapper(Map<String,String> props, RedisTableSchema redisTableSchema) {
        super(RedisCommand.SET,
                Integer.valueOf(props.getOrDefault(RedisValidator.REDIS_KEY_TTL,String.valueOf(0))));
        Util.checkArgument(props.get(REDIS_TABLE_NAME) != null, "Table name must be defined when sinking to redis");
        tableName = props.get(REDIS_TABLE_NAME);
        delimiter = props.getOrDefault(REDIS_DELIMITER,"_");
        this.redisTableSchema = redisTableSchema;
    }

    @Override
    public String getKeyFromData(Tuple2<Boolean, Row> data) {
//        StringUtils.j
        StringBuilder key = new StringBuilder(tableName + delimiter);
        for (Integer primaryKeyIndex : redisTableSchema.getPrimaryKeyInfo().getPrimaryKeyIndex()) {
            Object keyField = data.f1.getField(primaryKeyIndex);
            if (keyField == null){
                key.append(delimiter);
            }else{
                key.append(keyField).append(delimiter);
            }
        }
        return key.substring(0,key.length() -1 );
    }

    @Override
    public Map<String,String> getValueAsMap(Tuple2<Boolean, Row> data) {
        return null;
    }

    @Override
    // For now ,we take all the value in json as string.
    public String getValueAsJson(Tuple2<Boolean, Row> data, ObjectMapper objectMapper) throws Exception {
        Map<String,String> value = new HashMap<>();
        if (data.f0){
            Row r = data.f1;
            for (int i = 0; i < redisTableSchema.getFieldNames().length; i++) {
                Object fieldValue = r.getField(i);
                if (fieldValue != null){
                    value.put(redisTableSchema.getFieldNames()[i],fieldValue.toString());
                }else {
                    value.put(redisTableSchema.getFieldNames()[i],null);
                }
            }
            return objectMapper.writeValueAsString(value);
        }else{
            return null;
        }
    }
}

