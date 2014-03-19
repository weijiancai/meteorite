package com.meteorite.core.ui.annotation;

import com.meteorite.core.ui.layout.PropertyType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 布局属性注解
 *
 * @author wei_jc
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutPropertyElement {
    /**
     * 布局属性ID
     *
     * @return 返回布局属性ID
     */
    String id();

    /**
     * 布局属性名称，如果值是"##default"，则取【类名 + Form】作为表单的名称
     *
     * @return 返回布局属性的名称，例如ProjectConfigForm
     */
    String name() default "##default";

    /**
     * 布局属性显示名称
     *
     * @return 返回布局属性显示名称
     */
    String displayName();

    /**
     * 布局属性类型
     *
     * @return 返回布局属性类型
     */
    PropertyType type() default PropertyType.P;

    /**
     * 布局描述
     *
     * @return 返回布局描述
     */
    String desc() default "";

    /**
     * 默认值
     *
     * @return 返回默认值
     */
    String defaultValue() default "";

    /**
     * 排序号
     *
     * @return 返回排序号
     */
    int sortNum() default 0;
}
