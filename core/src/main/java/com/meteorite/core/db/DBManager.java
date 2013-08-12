package com.meteorite.core.db;

/**
 * @author wei_jc
 * @version 1.0
 */
public class DBManager {
    /**
     * 获得系统数据库sys
     *
     * @return 返回系统数据库sys
     */
    public static DataSource getSysDataSource() {
        return new DataSource("sys", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "");
    }
}
