package com.meteorite.core.util;

import java.util.Arrays;

/**
 * 字符串处理工具类
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class UString {
    public static final String EMPTY = "";

    public static String isEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 将数据库的表名，转换为类名，例如sys_dbms_define,转换后的结果是DbmsDefine
     *
     * @param tableName 数据库表名
     * @return 返回类名
     */
    public static String tableNameToClassName(String tableName) {
        tableName = tableName.toLowerCase();
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String str : tableName.split("_")) {
            if (i++ == 0 && tableName.startsWith("sys_")) {
                continue;
            }

            result.append(firstCharToUpper(str.toLowerCase()));
        }

        return result.toString();
    }

    public static String columnNameToFieldName(String columnName) {
        StringBuilder result = new StringBuilder();
        for (String str : columnName.split("_")) {
            result.append(firstCharToUpper(str.toLowerCase()));
        }

        return result.toString();
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 返回首字母大写的字符串
     */
    public static String firstCharToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 返回首字母小写的字符串
     */
    public static String firstCharToLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 获取字符串的非Null值，如果为null，返回“”
     *
     * @param str 原始字符串
     * @param defaultStr 默认值
     * @return 如果str非Null，返回str，否则返回defaultStr，如果defaultStr为Null，则返回“”
     */
    public static String getNotNull(String str, String defaultStr) {
        return isEmpty(str) ? (isEmpty(defaultStr) ? "" : defaultStr) : str;
    }

    /**
     * 将字符串转化为boolean值
     *
     * @param str 字符串
     * @return 如果str等于"T"或者"true"，则返回true，否则返回false
     */
    public static boolean toBoolean(String str) {
        return !isEmpty(str) && ("T".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str));
    }

    public static String substringByByte(String str, int i, int length) {
        return str.substring(i, i + length);
    }

    /**
     * 字符串左边填充n个字符c
     *
     * @param str 字符串
     * @param c 要填充的字符
     * @param length 字符窜总长度
     * @return 返回填充后的字符
     */
    public static String leftPadding(String str, char c, int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + str.length() < length) {
            sb.append(c);
        }
        return sb.toString() + str;
    }

    /**
     * 去掉两边的空白符，包括中文空白符" "
     *
     * @param str 字符串
     * @return 返回去掉空白符后的字符串
     */
    public static String trim(String str) {
        if(str == null) return null;

        str = str.trim();

        while (str.length() > 0 && Character.isSpaceChar(str.charAt(0))) {
            str = str.substring(1);
        }

        while (str.startsWith("　")) {
            str = str.substring(1);
        }

        while (str.length() > 0 && Character.isSpaceChar(str.charAt(str.length() - 1))) {
            str = str.substring(0, str.length() - 1);
        }

        while (str.endsWith("　")) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    /**
     * 取字符串中数字和小数点
     *
     * @param str 字符串
     * @return 返回数字字符串
     */
    public static String getNumber(String str) {
        if(UString.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()) {
            if((c >= '0' && c <= '9') || c == '.') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 判读字符串是否在字符串数组中
     *
     * @param array 字符串数据
     * @param str 字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否在数组中
     * @since 1.0.0
     */
    public static boolean inArray(String[] array, String str, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            for (String s : array) {
                if (s.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } else {
            return Arrays.binarySearch(array, str) >= 0;
        }
    }
}
