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
    NP("未知"),
    MP("主属性"),
    IP("明细属性"),
    AP("Action")
    ;

    private String displayName;

    private PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PropertyType getType(String typeStr) {
        if (MP.name().equals(typeStr)) {
            return MP;
        } else if (IP.name().equals(typeStr)) {
            return IP;
        } else if (AP.name().equals(typeStr)) {
            return AP;
        }

        return NP;
    }
}
