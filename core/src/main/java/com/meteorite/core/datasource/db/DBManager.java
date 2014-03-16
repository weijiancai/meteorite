package com.meteorite.core.datasource.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库管理
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DBManager {
    private static Map<String, DBDataSource> dataSourceMap = new HashMap<String, DBDataSource>();
    private static Map<String, DBConnection> connectionMap = new HashMap<String, DBConnection>();

    /**
     * 添加数据源
     *
     * @param dataSource 数据源
     */
    public static void addDataSource(DBDataSource dataSource) throws Exception {
        dataSourceMap.put(dataSource.getName(), dataSource);
    }

    /**
     * 获得数据源
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据源
     */
    public static DBDataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    /**
     * 获得系统数据库sys
     *
     * @return 返回系统数据库sys
     */
    public static DBDataSource getSysDataSource() {
        DBDataSource dataSource = dataSourceMap.get(SystemConfig.SYS_DB_NAME);
        if (dataSource == null) {
            dataSource = new DBDataSource(SystemConfig.SYS_DB_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "", SystemConfig.SYS_DB_VERSION);
            dataSourceMap.put(SystemConfig.SYS_DB_NAME, dataSource);
        }
        return dataSource;
    }

    /**
     * 获得所有数据库数据源
     *
     * @since 1.0.0
     * @return 返回所有数据库数据源
     */
    public static List<DBDataSource> getDataSources() {
        return new ArrayList<>(dataSourceMap.values());
    }

    /**
     * 获得数据库连接信息
     *
     * @param dataSource 数据源
     * @return 返回数据库连接信息
     */
    public static DBConnection getConnection(DBDataSource dataSource) throws Exception {
        DBConnection conn = connectionMap.get(dataSource.getName());
        if (conn == null) {
            conn = new DBConnectionImpl(dataSource);
            connectionMap.put(dataSource.getName(), conn);
        }
        return conn;
    }

    /**
     * 获得数据库连接信息
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据库连接信息
     */
    public static DBConnection getConnection(String dataSourceName) throws Exception {
        return getConnection(getDataSource(dataSourceName));
    }
}
