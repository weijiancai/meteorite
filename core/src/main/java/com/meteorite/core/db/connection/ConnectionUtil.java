package com.meteorite.core.db.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ConnectionUtil {
    /**
     * 关闭数据库连接
     *
     * @param connection 数据库连接
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
