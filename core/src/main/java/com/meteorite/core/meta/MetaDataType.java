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
    URL
}
