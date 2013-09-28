package com.meteorite.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Class工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class UClass {
    /**
     * 判定指定的 Class 对象是否表示一个基本类型。
     * 有九种预定义的 Class 对象，表示八个基本类型和 void。这些类对象由 Java 虚拟机创建，与其表示的基本类型同名，即 boolean、byte、char、short、int、long、float 和 double。
     * 另外再加上String
     *
     * @param clazz Class 对象
     * @return 返回此Class 对象是否是一个基本类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class;
    }

    /**
     * 将类对象转化为字符串
     *
     * @param clazz 类对象
     * @return 返回类对象的字符串
     */
    public static String toString(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getName()).append("\r\n");
        sb.append("Fields:\r\n");
        for (Field field : clazz.getDeclaredFields()) {
            sb.append("    ").append(field.getName()).append("\r\n");
        }
        sb.append("Methods:\r\n");
        for (Method method : clazz.getDeclaredMethods()) {
            sb.append("    ").append(method.getName()).append("\r\n");
        }
        return sb.toString();
    }
}
