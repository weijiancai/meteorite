package com.meteorite.core.meta.annotation;

import com.meteorite.core.meta.MetaDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元数据字段注解
 *
 * @author wei_jc
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaFieldElement {
    /**
     * 元数据字段名称，如果值是"##default"，则取方法名（去掉“get”）作为元数据字段的名称
     *
     * @return 返回元数据字段名称
     */
    String name() default "##default";

    /**
     * 元数据字段显示名称，如果值是"##default"，则取方法名（去掉“get”）作为元数据字段的名称
     *
     * @return 返回元数据字段显示名称
     */
    String displayName()default "##default";

    /**
     * 元数据字段描述信息
     *
     * @return 返回元数据字段描述信息
     */
    String desc() default "";

    /**
     * 元数据字段默认值
     *
     * @return 返回默认值
     */
    String defaultValue() default "";

    /**
     * 是否有效
     *
     * @return 如果有效 返回true， 否则false
     */
    boolean isValid() default true;

    /**
     * 元数据字段数据类型，默认字符串
     *
     * @return 返回元数据字段数据类型
     */
    MetaDataType dataType() default MetaDataType.STRING;

    /**
     * 数据字典ID
     *
     * @return 返回数据字典ID
     */
    String dictId() default "";

    /**
     * 排序号
     *
     * @return 返回排序号
     */
    int sortNum() default 0;

    /**
     * 最大长度
     *
     * @return 返回最大长度
     */
    int maxLength() default 0;
}
