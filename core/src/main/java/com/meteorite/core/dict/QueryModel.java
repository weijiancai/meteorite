package com.meteorite.core.dict;

import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;

/**
 * 查询模式
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum QueryModel {
    /**
     * 等于 =
     */
    EQUAL,
    /**
     * 不等于 !=
     */
    NOT_EQUAL,
    /**
     * 小于 <
     */
    LESS_THAN,
    /**
     * 小于等于 <=
     */
    LESS_EQUAL,
    /**
     * 大于 >
     */
    GREATER_THAN,
    /**
     * 大于等于 >=
     */
    GREATER_EQUAL,
    /**
     * 全Like like
     */
    LIKE,
    /**
     * 左Like like a%
     */
    LEFT_LIKE,
    /**
     * 右Like like %a
     */
    RIGHT_LIKE,
    /**
     * NULL
     */
    NULL,
    /**
     * NOT NULL
     */
    NOT_NULL
    ;

    public static QueryModel convert(String queryModelStr) {
        if (UString.isEmpty(queryModelStr)) {
            return EQUAL;
        }
        return QueryModel.valueOf(queryModelStr);
    }

    public String toSqlModel() {
        switch (this) {
            case EQUAL: {
                return " = ";
            }
            case NOT_EQUAL: {
                return " != ";
            }
            case LESS_THAN: {
                return " < ";
            }
            case LESS_EQUAL: {
                return " <= ";
            }
            case GREATER_THAN: {
                return " > ";
            }
            case GREATER_EQUAL: {
                return " >= ";
            }
            case LIKE: {
                return "%%";
            }
            case LEFT_LIKE: {
                return "*%";
            }
            case RIGHT_LIKE: {
                return "%*";
            }
            case NULL:
                return "null";
            case NOT_NULL:
                return "not null";
        }

        return "=";
    }
}
