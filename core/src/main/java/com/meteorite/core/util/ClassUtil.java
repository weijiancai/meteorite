package com.meteorite.core.util;

/**
 * Class工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ClassUtil {
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
}
