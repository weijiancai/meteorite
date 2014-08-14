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
        if (type.equals("CHECK")) {
            return CHECK;
        } else if (type.equals("PRIMARY KEY")) {
            return PRIMARY_KEY;
        } else if (type.equals("UNIQUE")) {
            return UNIQUE_KEY;
        } else if (type.equals("FOREIGN KEY")) {
            return FOREIGN_KEY;
        } else if (type.equals("VIEW CHECK")) {
            return VIEW_CHECK;
        } else if (type.equals("VIEW READONLY")) {
            return VIEW_READONLY;
        }

        return DEFAULT;
    }
}
