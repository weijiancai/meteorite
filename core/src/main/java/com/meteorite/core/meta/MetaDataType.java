package com.meteorite.core.meta;

import com.meteorite.core.dict.annotation.DictElement;

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
    BOOLEAN("是/否")
    ;

    private String displayName;

    private MetaDataType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MetaDataType getDataType(String dataTypeStr) {
        if (STRING.name().equalsIgnoreCase(dataTypeStr)) {
            return STRING;
        } else if (INTEGER.name().equalsIgnoreCase(dataTypeStr)) {
            return INTEGER;
        } else if (DOUBLE.name().equalsIgnoreCase(dataTypeStr)) {
            return DOUBLE;
        } else if (NUMBER.name().equalsIgnoreCase(dataTypeStr)) {
            return NUMBER;
        } else if (DATE.name().equalsIgnoreCase(dataTypeStr)) {
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
        }

        return STRING;
    }
}
