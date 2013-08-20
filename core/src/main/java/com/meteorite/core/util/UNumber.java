package com.meteorite.core.util;

/**
 * 数字工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UNumber {
    public static int toInt(String str) {
        return UString.isEmpty(str) ? 0 : Integer.parseInt(str);
    }
}
