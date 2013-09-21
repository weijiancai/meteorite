package com.meteorite.core.meta;

/**
 * 元数据数据类型枚举
 *
 * @author wei_jc
 * @version 1.0.0
 */
public enum MetaDataType {
    /**
     * 0. 字符串
     */
    STRING,
    /**
     * 1. 整数
     */
    INTEGER,
    /**
     * 2. 小数
     */
    DOUBLE,
    /**
     * 3. 数字类型（包括整数和小数）
     */
    NUMBER,
    /**
     * 4. 日期类型
     */
    DATE,
    /**
     * 5. EMAIL
     */
    EMAIL,
    /**
     * 6. IP
     */
    IP,
    /**
     * 7. URL
     */
    URL,
    /**
     * 8. DATA_SOURCE
     */
    DATA_SOURCE
    ;

    public static MetaDataType getDataType(String dataTypeStr) {
        if ("0".equals(dataTypeStr)) {
            return STRING;
        } else if ("1".equals(dataTypeStr)) {
            return INTEGER;
        } else if ("2".equals(dataTypeStr)) {
            return DOUBLE;
        } else if ("3".equals(dataTypeStr)) {
            return NUMBER;
        } else if ("4".equals(dataTypeStr)) {
            return DATE;
        } else if ("5".equals(dataTypeStr)) {
            return EMAIL;
        } else if ("6".equals(dataTypeStr)) {
            return IP;
        } else if ("7".equals(dataTypeStr)) {
            return URL;
        } else if ("8".equals(dataTypeStr)) {
            return DATA_SOURCE;
        }

        return STRING;
    }
}
