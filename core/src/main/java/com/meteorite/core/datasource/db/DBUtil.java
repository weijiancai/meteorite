package com.meteorite.core.datasource.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.DataSourceType;
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
        return exitsTable(SystemConfig.SYSTEM_NAME, tableName);
    }

    public static boolean exitsTable(String dataSourceName, String tableName) throws Exception {
        DataSource dataSource = DataSourceManager.getDataSource(dataSourceName);
        if (dataSource.getType() != DataSourceType.DATABASE) {
            throw new Exception(String.format("此数据源【%s】非数据库数据源", dataSourceName));
        }
        DBConnection conn = ((DBDataSource)dataSource).getDbConnection();
        return existsTable(conn, tableName);
    }

    public static boolean existsTable(DBConnection conn, String tableName) throws Exception {
        return conn.getSchema().getTable(tableName) != null;
    }
}
