package com.meteorite.core.db.object.impl;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.db.object.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 0.0.1
 */
public class DBConnectionImpl implements DBConnection {
    private String username;
    private String password;
    private String url;
    private String driverClass;

    public DBConnectionImpl(String username, String password, String url, String driverClass) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driverClass = driverClass;
    }

    public DBConnectionImpl(DataSource dataSource) {
        this.username = dataSource.getUsername();
        this.password = dataSource.getPassword();
        this.url = dataSource.getUrl();
        this.driverClass = dataSource.getDriverClass();
    }

    @Override
    public Connection getConnection() throws Exception {
        Class.forName(getDriverClass());
        return  DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }

    @Override
    public List<Map<String, Object>> getResultSet(String sql) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


        while (rs.next()) {
            Map<String, Object> map = new HashMap<String, Object>();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                map.put(metaData.getColumnLabel(i), rs.getObject(i));
            }

            list.add(map);
        }
        rs.close();
        stmt.close();
        conn.close();

        return list;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
