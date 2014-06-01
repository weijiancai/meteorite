package com.meteorite.fxbase.ui;

/**
 * 值转换器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class ValueConverter<T> {
    /**
     * 将对象转换为字符串
     *
     * @param object 要转换的对象
     * @return 返回字符串
     */
    public abstract String toString(T object);

    /**
     * 将字符串转换为对象
     *
     * @param string 要转换的字符串
     * @return 返回对象
     */
    public abstract T fromString(String string);
}
