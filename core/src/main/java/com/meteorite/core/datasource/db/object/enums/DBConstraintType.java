package com.meteorite.core.datasource.db.object.enums;

/**
 * 约束类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum DBConstraintType {
    DEFAULT,
    CHECK,
    PRIMARY_KEY,
    UNIQUE_KEY,
    FOREIGN_KEY,
    VIEW_CHECK,
    VIEW_READONLY
    ;

    public static DBConstraintType convert(String type) {
        switch (type) {
            case "CHECK": return CHECK;
            case "PRIMARY KEY": return PRIMARY_KEY;
            case "UNIQUE": return UNIQUE_KEY;
            case "FOREIGN KEY": return FOREIGN_KEY;
            case "VIEW CHECK": return VIEW_CHECK;
            case "VIEW READONLY": return VIEW_READONLY;
        }

        return DEFAULT;
    }
}
