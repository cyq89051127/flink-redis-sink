package com.cyq.flink.redis;

import org.apache.flink.table.types.DataType;

import java.io.Serializable;

/**
 * @author chaiyongqiang
 */
public class RedisTableSchema implements Serializable {
//    private static final long serialVersionUID = 1L;

    public DataType[] getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(DataType[] fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public PrimaryKeyInfo getPrimaryKeyInfo() {
        return primaryKeyInfo;
    }

    public void initializePrimaryKey (String [] primaryKeyFields,
                                      Integer[] primaryKeyIndex,
                                      DataType[] primaryKeyTypes){
        primaryKeyInfo = new PrimaryKeyInfo(primaryKeyFields,primaryKeyIndex,primaryKeyTypes);
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    private String[] fieldNames;
    private DataType[] fieldTypes;
    private PrimaryKeyInfo primaryKeyInfo;

    static class PrimaryKeyInfo implements Serializable {
        public static final long serialVersionUID = 1L;

        public PrimaryKeyInfo(String [] primaryKeyFields, Integer[] primaryKeyIndex, DataType[] primaryKeyTypes) {
            this.primaryKeyFields = primaryKeyFields;
            this.primaryKeyIndex = primaryKeyIndex;
            this.primaryKeyTypes = primaryKeyTypes;
        }

        public String[] getPrimaryKeyFields() {
            return primaryKeyFields;
        }

        public void setPrimaryKeyFields(String[] primaryKeyFields) {
            this.primaryKeyFields = primaryKeyFields;
        }

        public Integer[] getPrimaryKeyIndex() {
            return primaryKeyIndex;
        }

        public void setPrimaryKeyIndex(Integer[] primaryKeyIndex) {
            this.primaryKeyIndex = primaryKeyIndex;
        }

        public DataType[] getPrimaryKeyTypes() {
            return primaryKeyTypes;
        }

        public void setPrimaryKeyTypes(DataType[] primaryKeyTypes) {
            this.primaryKeyTypes = primaryKeyTypes;
        }

        String[] primaryKeyFields = null;
        Integer[] primaryKeyIndex = null;
        DataType[] primaryKeyTypes = null;
    }

}
