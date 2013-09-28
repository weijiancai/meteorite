package com.meteorite.core.ui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表单注解
 *
 * @author wei_jc
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormElement {
    /**
     * 表单名称，如果值是"##default"，则取【类名 + Form】作为表单的名称
     *
     * @return 返回表单的名称，例如ProjectConfigForm
     */
    String getName() default "##default";

    /**
     * 表单显示名称
     *
     * @return 返回元表单显示名称
     */
    String getDisplayName();

    /**
     * 列数，默认值3列
     *
     * @return 返回列数
     */
    int getColCount() default 3;

    /**
     * 列宽，默认值180
     *
     * @return 返回列宽
     */
    int getColWidth() default 180;

    /**
     * Label右边的间隔，默认值5
     *
     * @return 返回Label右边的间隔
     */
    int getLabelGap() default 5;

    /**
     * Field右边的间隔，默认值15
     *
     * @return 返回Field右边的间隔
     */
    int getFieldGap() default 15;

    /**
     * 表单的行间隔，默认值3
     *
     * @return 返回表单的行间隔
     */
    int getHgap() default 3;

    /**
     * 表单的列间隔，默认值5
     *
     * @return 返回表单的列间隔
     */
    int getVgap() default 5;
}
