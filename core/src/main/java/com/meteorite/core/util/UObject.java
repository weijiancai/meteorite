package com.meteorite.core.util;

/**
 * 对象工具类
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class UObject {
    /**
     * 将对象转化为字符串
     *
     * @param obj 对象
     * @return 返回字符串
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }
}
