package com.meteorite.core.meta;

import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.util.UString;

/**
 * 元数据数据类型枚举
 *
 * @author wei_jc
 * @version 1.0.0
 */
@DictElement(categoryName = "数据类型")
public enum MetaDataType {
    /**
     * 0. 字符串
     */
    STRING("字符串"),
    /**
     * 1. 整数
     */
    INTEGER("整数"),
    /**
     * 2. 小数
     */
    DOUBLE("小数"),
    /**
     * 3. 数字类型（包括整数和小数）
     */
    NUMBER("数字"),
    /**
     * 4. 日期类型
     */
    DATE("日期"),
    /**
     * 5. EMAIL
     */
    EMAIL("邮件"),
    /**
     * 6. IP
     */
    IP("IP"),
    /**
     * 7. URL
     */
    URL("URL"),
    /**
     * 8. DATA_SOURCE
     */
    DATA_SOURCE("数据源"),
    /**
     * 9. 密码
     */
    PASSWORD("密码"),
    /**
     * 10. 数据字典
     */
    DICT("数据字典"),
    /**
     * 11.
     */
    BOOLEAN("是/否"),
    /**
     * 二进制数据
     */
    BLOB("二进制"),
    /**
     * GUID
     */
    GUID("GUID")
    ;

    private static String[] stringArray = {STRING.name(), "CHARACTER VARYING", "varchar", "text", "longtext", "mediumtext", "char"};
    private static String[] intArray = {INTEGER.name(), "BIGINT", "INTEGER", "SMALLINT", "int", "tinyint"};
    private static String[] dateArray = {DATE.name(), "TIMESTAMP", "DATE", "datetime", "time"};
    private static String[] doubleArray = {DOUBLE.name(), "decimal", "double"};
    private static String[] blobArray = {BLOB.name(), "longblob", "blob"};

    private String displayName;

    private MetaDataType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MetaDataType getDataType(String dataTypeStr) {
        if (UString.inArray(stringArray, dataTypeStr, true)) {
            return STRING;
        } else if (UString.inArray(intArray, dataTypeStr, true)) {
            return INTEGER;
        } else if (UString.inArray(doubleArray, dataTypeStr, true)) {
            return DOUBLE;
        } else if (NUMBER.name().equalsIgnoreCase(dataTypeStr)) {
            return NUMBER;
        } else if (UString.inArray(dateArray, dataTypeStr, true)) {
            return DATE;
        } else if (EMAIL.name().equalsIgnoreCase(dataTypeStr)) {
            return EMAIL;
        } else if (IP.name().equalsIgnoreCase(dataTypeStr)) {
            return IP;
        } else if (URL.name().equalsIgnoreCase(dataTypeStr)) {
            return URL;
        } else if (DATA_SOURCE.name().equalsIgnoreCase(dataTypeStr)) {
            return DATA_SOURCE;
        } else if (PASSWORD.name().equalsIgnoreCase(dataTypeStr)) {
            return PASSWORD;
        } else if (BOOLEAN.name().equalsIgnoreCase(dataTypeStr)) {
            return BOOLEAN;
        } else if (DICT.name().equalsIgnoreCase(dataTypeStr)) {
            return DICT;
        } else if (UString.inArray(blobArray, dataTypeStr, true)) {
            return BLOB;
        } else if (GUID.name().equalsIgnoreCase(dataTypeStr)) {
            return GUID;
        }

        return STRING;
    }

    /**
     * 将元数据数据类型转换为Java数据类型
     *
     * @return 返回Java数据类型
     */
    public String toJavaType() {
        switch (this) {
            case INTEGER : return "int";
            case DOUBLE : return "double";
            case NUMBER : return "float";
            case DATE : return "Date";
            case BOOLEAN : return "boolean";
        }
        return "String";
    }
}
