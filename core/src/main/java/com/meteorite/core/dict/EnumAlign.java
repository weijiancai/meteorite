package com.meteorite.core.dict;

import com.meteorite.core.dict.annotation.DictElement;

/**
 * 对齐方式枚举
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "对齐方式")
public enum EnumAlign {
    LEFT("左对齐"),
    CENTER("居中对齐"),
    RIGHT("右对齐")
    ;

    private String displayName;

    private EnumAlign(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EnumAlign getAlign(String align) {
        if (CENTER.name().equals(align)) {
            return CENTER;
        } else if (RIGHT.name().equals(align)) {
            return RIGHT;
        }
        return LEFT;
    }
}
