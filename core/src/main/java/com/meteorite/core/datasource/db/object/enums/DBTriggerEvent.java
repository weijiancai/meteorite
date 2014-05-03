package com.meteorite.core.datasource.db.object.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 触发器事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum DBTriggerEvent {
    INSERT,
    UPDATE,
    DELETE,
    TRUNCATE,
    DROP,
    UNKNOWN;

    public static List<DBTriggerEvent> convertToList(String event) {
        List<DBTriggerEvent> result = new ArrayList<>();

        if (event.contains("INSERT")) {
            result.add(INSERT);
        }
        if (event.contains("UPDATE")) {
            result.add(UPDATE);
        }
        if (event.contains("DELETE")) {
            result.add(DELETE);
        }
        if (event.contains("TRUNCATE")) {
            result.add(TRUNCATE);
        }
        if (event.contains("DROP")) {
            result.add(DROP);
        }

        return result;
    }
}
