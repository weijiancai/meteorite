package com.meteorite.core.datasource.db.util;

import com.meteorite.core.util.UString;

/**
 * Sql语句工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlUtil {
    /**
     * 将sql预编译语句代入值输出
     *
     * @return 返回sql语句
     */
    public static String toLog(String sql, Object... paramQueue) {
        if (UString.isEmpty(sql)) {
            return "";
        }
        if (!sql.contains("?")) {
            return sql;
        }

        String result = sql;
        for (Object obj : paramQueue) {
            int idx = result.indexOf("?");
            if (idx > -1) {
                result = result.replaceFirst("\\?", "'" + obj + "'");
            } else {
                result += "<" + obj + "> ";
            }
        }
        return result;
    }
}
