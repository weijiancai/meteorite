package com.meteorite.core.dict;

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
    RIGHT_LIKE
    ;

    public static QueryModel convert(String queryModelStr) {
        if (UString.isEmpty(queryModelStr)) {
            return EQUAL;
        }
        return QueryModel.valueOf(queryModelStr);
    }
}
