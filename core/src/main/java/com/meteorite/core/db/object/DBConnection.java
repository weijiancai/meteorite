package com.meteorite.core.db.object;

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

    List<Map<String, Object>> getResultSet(String sql) throws Exception;
}
