package com.meteorite.core.db.object;

import com.meteorite.core.db.DatabaseType;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 数据库连接
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBConnection {
    Connection getConnection() throws Exception;

    /**
     * 获得结果集
     *
     * @param sql sql语句
     * @return 返回查询结果集
     * @throws Exception
     */
    List<Map<String, Object>> getResultSet(String sql) throws Exception;

    /**
     * 获得数据库加载器
     *
     * @return 返回数据库加载器
     */
    DBLoader getLoader() throws Exception;

    /**
     * 获得数据库类型
     *
     * @return 返回数据库类型
     */
    DatabaseType getDatabaseType() throws Exception;

    /**
     * 获得当前连接的Schema
     *
     * @return 返回当前连接的Schema
     * @throws Exception
     */
    DBSchema getSchema() throws Exception;

    /**
     * 执行SQL脚本文件
     *
     * @param sqlFile SQL脚本文件
     * @throws Exception
     */
    void execSqlFile(File sqlFile) throws Exception;
}
