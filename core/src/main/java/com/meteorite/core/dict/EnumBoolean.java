package com.meteorite.core.dict;

import com.meteorite.core.dict.annotation.DictElement;

/**
 * Boolean类型枚举
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "Boolean类型（T/F）")
public enum EnumBoolean {
    T("是"),
    F("否")
    ;

    private String displayName;

    private EnumBoolean(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
