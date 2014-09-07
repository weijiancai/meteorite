package com.meteorite.core.meta;

import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.util.UString;

/**
 * 默认值枚举
 *
 * @author wei_jc
 * @version 1.0.0
 */
@DictElement(categoryName = "默认值")
public enum DefaultValues {
    /**
     * GUID
     */
    GUID("GUID"),
    /**
     * n
     */
    SYS_DATE("系统当前时间")
    ;

    private String displayName;

    private DefaultValues(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
