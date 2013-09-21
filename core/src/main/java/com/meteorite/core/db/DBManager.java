package com.meteorite.core.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.db.object.DBConnection;
import com.meteorite.core.db.object.impl.DBConnectionImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0
 */
public class DBManager {
    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();
    private static Map<String, DBConnection> connectionMap = new HashMap<String, DBConnection>();

    /**
     * 添加数据源
     *
     * @param dataSource 数据源
     */
    public static void addDataSource(DataSource dataSource) throws Exception {
        dataSourceMap.put(dataSource.getName(), dataSource);
    }

    /**
     * 获得数据源
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据源
     */
    public static DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    /**
     * 获得系统数据库sys
     *
     * @return 返回系统数据库sys
     */
    public static DataSource getSysDataSource() {
        return new DataSource(SystemConfig.SYS_DB_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "", SystemConfig.SYS_DB_VERSION);
    }

    /**
     * 获得数据库连接信息
     *
     * @param dataSource 数据源
     * @return 返回数据库连接信息
     */
    public static DBConnection getConnection(DataSource dataSource) throws Exception {
        DBConnection conn = connectionMap.get(dataSource.getName());
        if (conn == null) {
            connectionMap.put(dataSource.getName(), new DBConnectionImpl(dataSource));
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
