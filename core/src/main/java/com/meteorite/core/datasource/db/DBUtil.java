package com.meteorite.core.datasource.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.datasource.db.object.DBConnection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    /**
     * 将字符串转换为资源相对路径
     *
     * @param str 要转换的字符串，例如tableName.columnName
     * @return 返回资源相对路径
     */
    public static String toColumnPath(String str) {
        String[] strs = str.split(".");
        if (strs.length != 2) {
            throw new RuntimeException("转换资源路径失败，格式：tableName.columnName");
        }
        return String.format("table/%s/column/%s", strs[0], strs[1]);
    }
}
