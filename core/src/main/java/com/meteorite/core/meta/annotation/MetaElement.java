package com.meteorite.core.meta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元数据注解
 *
 * @author wei_jc
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaElement {
    /**
     * 元数据名称，如果值是"##default"，则取类名作为元数据的名称
     *
     * @return 返回元数据名称
     */
    String name() default "##default";

    /**
     * 元数据中文名称
     *
     * @return 返回元数据中文名称
     */
    String cname();

    /**
     * 元数据描述信息
     *
     * @return 返回元数据描述信息
     */
    String desc() default "";

    /**
     * 是否有效
     *
     * @return 如果有效 返回true， 否则false
     */
    boolean isValid() default true;
}
