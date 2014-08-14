package com.meteorite.core.datasource.db.object.enums;

/**
 * 触发器类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum DBTriggerType {
    BEFORE,
    AFTER,
    INSTEAD_OF,
    UNKNOWN;

    public static DBTriggerType convert(String type) {
        if (type.equals("BEFORE")) {
            return BEFORE;
        } else if (type.equals("AFTER")) {
            return AFTER;
        } else if (type.equals("INSTEAD OF")) {
            return INSTEAD_OF;
        }
        return UNKNOWN;
    }
}
