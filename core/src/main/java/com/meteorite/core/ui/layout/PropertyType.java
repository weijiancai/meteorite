package com.meteorite.core.ui.layout;

import com.meteorite.core.dict.annotation.DictElement;

/**
 * 布局属性类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryId = "LayoutPropertyType", categoryName = "布局属性类型")
public enum PropertyType {
    N("未知"),
    P("属性"),
    A("Action")
    ;

    private String displayName;

    private PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PropertyType getType(String typeStr) {
        if (P.name().equals(typeStr)) {
            return P;
        } else if (A.name().equals(typeStr)) {
            return A;
        }

        return N;
    }
}
