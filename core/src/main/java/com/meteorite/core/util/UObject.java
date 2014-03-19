package com.meteorite.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
     * @since 1.0.0
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 两个对象之间的数据克隆
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void clone(Object source, Object target) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = source.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String setMethodName = "set" + UString.firstCharToUpper(fieldName);
            String getMethodName = "get" + UString.firstCharToUpper(fieldName);
            if(fieldName.startsWith("is")) {
                setMethodName = "set" + fieldName.substring(2);
                getMethodName = fieldName;
            }
            try {
                Method setMethod = clazz.getMethod(setMethodName, field.getType());
                Method getMethod = clazz.getMethod(getMethodName);
                setMethod.invoke(target, getMethod.invoke(source));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
