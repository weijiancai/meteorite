package com.meteorite.core.dict.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据字典注解
 *
 * @author wei_jc
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictElement {
    /**
     * 字典分类ID，如果值是"##default"，则取【类名】作为字典分类ID
     *
     * @return 返回字典分类ID
     */
    String categoryId() default "##default";

    /**
     * 字典分类名称
     *
     * @return 返回字典分类名称
     */
    String categoryName();

    /**
     * 字典代码ID，默认值“name”，取枚举的name值；“ordinal”，取枚举的序号值
     *
     * @return 返回字典代码ID
     */
    String codeNameMethod() default "name";

    /**
     * 代码名称的方法名
     *
     * @return 返回代码名称的方法名
     */
    String codeValueMethod() default "getDisplayName";
}
