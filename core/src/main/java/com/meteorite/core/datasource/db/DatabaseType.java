package com.meteorite.core.datasource.db;

import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.util.UString;

/**
 * 数据库类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
@DictElement(categoryName = "数据库类型")
public enum DatabaseType {
    ORACLE("ORACLE", "Oracle"),
    MYSQL("MYSQL", "MySQL"),
    SQLSERVER("SQLSERVER", "SqlServer"),
    HSQLDB("HSQLDB", "Hsqldb"),
    UNKNOWN("UNKNOWN", "Unknown Database");

    private String name;
    private String displayName;

    DatabaseType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DatabaseType get(String name) {
        if(UString.isEmpty(name)) return UNKNOWN;

        for (DatabaseType databaseType : values()) {
            if (name.equalsIgnoreCase(databaseType.getName())) return databaseType;
        }

        return UNKNOWN;
    }
}
