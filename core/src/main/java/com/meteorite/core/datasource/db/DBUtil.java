package com.meteorite.core.datasource.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.db.object.DBConnection;

/**
 * 数据库工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class DBUtil {
    /**
     * 系统数据源是否存在表
     *
     * @param tableName 表明
     * @return 存在表，返回true，否则false
     */
    public static boolean existsTable(String tableName) throws Exception {
        return exitsTable(SystemConfig.SYS_DB_NAME, tableName);
    }

    public static boolean exitsTable(String dataSourceName, String tableName) throws Exception {
        DBConnection conn = DBManager.getConnection(dataSourceName);
        return existsTable(conn, tableName);
    }

    public static boolean existsTable(DBConnection conn, String tableName) throws Exception {
        return conn.getSchema().getTable(tableName) != null;
    }
}
