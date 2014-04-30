package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.DBLoader;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.loader.HsqldbLoader;
import com.meteorite.core.datasource.db.object.loader.MySqlLoader;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接实现类
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DBConnectionImpl implements DBConnection {
    private DBDataSource dataSource;
    private DBLoader loader;
    private List<DBSchema> schemas;
    private DBSchema currentSchema; // 当前连接Schema
    private DatabaseType dbType = DatabaseType.UNKNOWN;

    public DBConnectionImpl(DBDataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        // 初始化数据库类型
        initDatabaseType();
        // 初始化加载器
        initLoader();
        // 加载Schema
        schemas = loader.loadSchemas();
        // 初始化当前Schema
        initCurrentSchema();
        // 设置数据源的数据库类型
        dataSource.setDatabaseType(dbType);
    }

    // 初始化数据库类型
    private void initDatabaseType() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            String productName = metaData.getDatabaseProductName().toUpperCase();
            if (productName.contains("ORACLE")) {
                dbType = DatabaseType.ORACLE;
            } else if (productName.contains("MYSQL")) {
                dbType = DatabaseType.MYSQL;
            } else if (productName.contains("SQL SERVER")) {
                dbType = DatabaseType.SQLSERVER;
            } else if (productName.contains("HSQL")) {
                dbType = DatabaseType.HSQLDB;
            }
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

    }

    private void initCurrentSchema() throws Exception {
        Connection conn = getConnection();
        for (DBSchema schema : schemas) {
            if (schema.getName().equalsIgnoreCase(conn.getCatalog())) {
                currentSchema = schema;
            }
        }
    }

    // 初始化加载器
    private void initLoader() throws Exception {
        switch (dbType) {
            case MYSQL:
                loader = new MySqlLoader(this);
                break;
            case HSQLDB:
                loader = new HsqldbLoader(this);
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        Class.forName(dataSource.getDriverClass());
        return  DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
    }

    @Override
    public List<DataMap> getResultSet(String sql) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();

        List<DataMap> list = new ArrayList<>();

        while (rs.next()) {
            DataMap map = new DataMap();

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
        return loader;
    }

    @Override
    public DatabaseType getDatabaseType() throws Exception {
        return dbType;
    }

    @Override
    public DBSchema getSchema() throws Exception {
        return currentSchema;
    }

    @Override
    public List<DBSchema> getSchemas() throws Exception {
        return schemas;
    }

    @Override
    public void execSqlFile(File sqlFile) throws Exception {
        String[] sqls = UFile.readString(sqlFile).split(";");
        Connection conn = null;
        try {
            conn = getConnection();
            for (String sql : sqls) {
                if(UString.isEmpty(sql)) {
                    continue;
                }
                System.out.println("SQL = " + sql);
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