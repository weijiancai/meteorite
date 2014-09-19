package com.meteorite.core.util.group;

import com.meteorite.core.dict.annotation.DictElement;

/**
 * 分组函数枚举
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "分组函数")
public enum GroupFunction {
    /**
     * 求总数，去重
     */
    COUNT("COUNT"),
    /**
     * 求和
     */
    SUM("SUM")
    ;

    private String displayName;

    private GroupFunction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static GroupFunction getGroupFunction(String functionName) {
        if (COUNT.name().equals(functionName)) {
            return COUNT;
        } else if (SUM.name().equals(functionName)) {
            return SUM;
        }
        return SUM;
    }
}
