package com.meteorite.fxbase.ui;

/**
 * 可录入控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ICanInput<T> {
    /**
     * 获得录入控件的名称
     *
     * @return 返回控件名称
     */
    String getName();

    /**
     * 获得录入控件的值
     *
     * @return 返回录入控件的值
     */
    T getInputValue();

    /**
     * 设置转化对象
     *
     * @param convert 转化器
     */
    void setValueConvert(ValueConverter<T> convert);

    /**
     * 获得值对象得字符串，如果设置了转换器，则调用转换器转化。没有设置，则调用对象的toString()方法
     *
     * @return 返回值对象的字符串
     */
    String getValueString();
}
