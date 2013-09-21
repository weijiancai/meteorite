package com.meteorite.core.db.object.impl;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.db.DatabaseType;
import com.meteorite.core.db.connection.ConnectionUtil;
import com.meteorite.core.db.object.DBConnection;
import com.meteorite.core.db.object.DBLoader;
import com.meteorite.core.db.object.DBSchema;
import com.meteorite.core.db.object.loader.HsqldbLoader;
import com.meteorite.core.db.object.loader.MySqlLoader;
import com.meteorite.core.util.UtilFile;

import java.io.File;
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
    private DataSource dataSource;
    private DBLoader loader;
    private List<DBSchema> schemas;
    private DBSchema currentSchema; // 当前连接Schema

    public DBConnectionImpl(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        loader = getLoader();
        // 加载Schema
        schemas = loader.loadSchemas();
        // 初始化当前Schema
        initCurrentSchema();
    }

    private void initCurrentSchema() throws Exception {
        Connection conn = getConnection();
        for (DBSchema schema : schemas) {
            if (schema.getName().equalsIgnoreCase(conn.getCatalog())) {
                currentSchema = schema;
            }
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        Class.forName(dataSource.getDriverClass());
        return  DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
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

    @Override
    public DBLoader getLoader() throws Exception {
        DatabaseType type = getDatabaseType();
        switch (type) {
            case MYSQL:
                return new MySqlLoader(this);
            case HSQLDB:
                return new HsqldbLoader(this);
        }

        return null;
    }

    @Override
    public DatabaseType getDatabaseType() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            String productName = metaData.getDatabaseProductName().toUpperCase();
            if (productName.contains("ORACLE")) {
                return DatabaseType.ORACLE;
            } else if (productName.contains("MYSQL")) {
                return DatabaseType.MYSQL;
            } else if (productName.contains("SQL SERVER")) {
                return DatabaseType.SQLSERVER;
            } else if (productName.contains("HSQL")) {
                return DatabaseType.HSQLDB;
            }
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

        return DatabaseType.UNKNOWN;
    }

    @Override
    public DBSchema getSchema() throws Exception {
        return currentSchema;
    }

    @Override
    public void execSqlFile(File sqlFile) throws Exception {
        String[] sqls = UtilFile.readString(sqlFile).split(";");
        Connection conn = null;
        try {
            conn = getConnection();
            for (String sql : sqls) {
                System.out.println(sql);
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.commit();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }

        }
    }
}
