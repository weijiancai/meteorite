package com.meteorite.core.datasource;

import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.util.UString;

/**
 * 数据源类型：数据库、文件系统
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "数据源类型")
public enum DataSourceType {
    /**
     * 数据库
     */
    DATABASE("DATABASE", "数据库"),
    /**
     * 文件系统
     */
    FILE_SYSTEM("FILE_SYSTEM", "文件系统"),
    /**
     * 类路径
     */
    CLASS_PATH("CLASS_PATH", "类路径"),
    /**
     * 未知
     */
    UNKNOWN("UNKNOWN", "未知");

    private String name;
    private String displayName;

    DataSourceType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DataSourceType get(String name) {
        if(UString.isEmpty(name)) return UNKNOWN;

        for (DataSourceType type : values()) {
            if (name.equalsIgnoreCase(type.getName())) return type;
        }

        return UNKNOWN;
    }
}
