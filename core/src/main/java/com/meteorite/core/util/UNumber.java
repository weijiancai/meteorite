package com.meteorite.core.util;

/**
 * 数字工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UNumber {
    public static int toInt(String str) {
        try {
            return UString.isEmpty(str) ? 0 : Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double toDouble(String str) {
        try {
            return UString.isEmpty(str) ? 0 : Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return UString.isEmpty(str) ? 0 : Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
