package com.meteorite.core.ui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 布局注解
 *
 * @author wei_jc
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutElement {
    /**
     * 布局ID
     *
     * @return 返回布局ID
     */
    String id();

    /**
     * 布局父ID
     *
     * @return 返回布局父ID
     */
    String pid() default "";

    /**
     * 布局名称，如果值是"##default"，则取【类名 + Form】作为表单的名称
     *
     * @return 返回布局的名称，例如ProjectConfigForm
     */
    String name() default "##default";

    /**
     * 布局显示名称
     *
     * @return 返回布局显示名称
     */
    String displayName();

    /**
     * 布局描述
     *
     * @return 返回布局描述
     */
    String desc() default "";

    /**
     * 排序号
     *
     * @return 返回排序号
     */
    int sortNum() default 0;
}
