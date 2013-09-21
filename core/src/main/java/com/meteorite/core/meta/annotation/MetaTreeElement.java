package com.meteorite.core.meta.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元数据树形注解
 *
 * @author wei_jc
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaTreeElement {
    String id() default "getId";

    String pid() default "getPid";

    String name() default "getName";

    String displayName() default "getDisplayName";

    String children() default "getChildren";

    String isParent() default "isParent";

    String sortOrder() default "getSortOrder";

    boolean usePid() default true;
}
