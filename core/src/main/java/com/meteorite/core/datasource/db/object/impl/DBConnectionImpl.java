package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.DBLoader;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.loader.HsqldbLoader;
import com.meteorite.core.datasource.db.object.loader.MySqlLoader;
import com.meteorite.core.datasource.db.object.loader.SqlServerLoader;
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
    private boolean isAvailable;

    public DBConnectionImpl(DBDataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        // 初始化数据库类型
        initDatabaseType();
        // 初始化加载器
        initLoader();
        // 设置数据源的数据库类型
        dataSource.setDatabaseType(dbType);
    }

    // 初始化数据库类型
    private void initDatabaseType() throws Exception {
        if (dataSource.getDatabaseType() != null) {
            dbType = dataSource.getDatabaseType();
            return;
        }

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

    // 初始化加载器
    private void initLoader() throws Exception {
        switch (dbType) {
            case MYSQL:
                loader = new MySqlLoader(this);
                break;
            case HSQLDB:
                loader = new HsqldbLoader(this);
                break;
            case SQLSERVER:
                loader = new SqlServerLoader(this);
                break;
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        Connection conn;
        try {
            Class.forName(dataSource.getDriverClass());
            if (dbType == DatabaseType.HSQLDB && UString.isNotEmpty(dataSource.getFilePath())) {
                conn = DriverManager.getConnection("jdbc:hsqldb:file:" + dataSource.getFilePath(), dataSource.getUsername(), dataSource.getPassword());
            } else {
                conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            }
            if (conn != null) {
                isAvailable = true;
            }
        } catch (Exception e) {
            isAvailable = false;
            throw e;
        }

        return conn;
    }

    @Override
    public List<DataMap> getResultSet(String sql) throws Exception {
        List<DataMap> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
//            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                DataMap map = new DataMap();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    map.put(metaData.getColumnLabel(i), rs.getObject(i));
                }

                list.add(map);
            }
            rs.close();
            stmt.close();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

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

    @Override
    public DBDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public boolean isAvailable() {
        if (!isAvailable) {
            Connection conn = null;
            try {
                conn = getConnection();
                if (conn != null) {
                    isAvailable = true;
                }
            } catch (Exception e) {
                isAvailable = false;
            } finally {
                ConnectionUtil.closeConnection(conn);
            }
        }
        return isAvailable;
    }

    public void setSchemas(List<DBSchema> schemas) {
        this.schemas = schemas;
    }

    public void setCurrentSchema(DBSchema currentSchema) {
        this.currentSchema = currentSchema;
    }
}
