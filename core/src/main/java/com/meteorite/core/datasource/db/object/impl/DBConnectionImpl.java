package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
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
import com.meteorite.core.datasource.eventdata.SqlExecuteEventData;
import com.meteorite.core.observer.BaseSubject;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(DBConnectionImpl.class);
    private DBDataSource dataSource;
    private DBLoader loader;
    private List<DBSchema> schemas;
    private DBSchema currentSchema; // 当前连接Schema
    private DatabaseType dbType = DatabaseType.UNKNOWN;
    private boolean isAvailable;
    private Subject<SqlExecuteEventData> sqlExecuteSubject = new BaseSubject<SqlExecuteEventData>();

    public DBConnectionImpl(DBDataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        // 初始化数据库类型
        initDatabaseType();
        // 初始化加载器
        initLoader();
    }

    // 初始化数据库类型
    private void initDatabaseType() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn == null) {
                return;
            }
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

            // 当前schema
            DBSchemaImpl schema = new DBSchemaImpl();
            String schemaName = null;
            /*if(dbType == DatabaseType.SQLSERVER) {
                // SqlServer getSchema() 会报错误 java.lang.AbstractMethodError: com.microsoft.sqlserver.jdbc.SQLServerConnection.getSchema()Ljava/lang/String
                schemaName = conn.getCatalog();
            } else {
                schemaName = conn.getSchema();
            }*/
            try {
                schemaName = conn.getCatalog();
            } catch (AbstractMethodError e) {
                e.printStackTrace();
                schemaName = conn.getSchema();
            }

            if (UString.isEmpty(schemaName)) {
                schemaName = conn.getCatalog();
            }
            schema.setDataSource(dataSource);
            schema.setName(schemaName);

            currentSchema = schema;
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

        if (currentSchema != null) {
            ((DBSchemaImpl)currentSchema).setLoader(loader);
        }
    }

    @Override
    @JSONField(serialize = false)
    public Connection getConnection() throws Exception {
        Connection conn = null;
        try {
            Class.forName(dataSource.getDriverClass());
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPwd());
            /*if (dbType == DatabaseType.HSQLDB && UString.isNotEmpty(dataSource.getFilePath())) {
                conn = DriverManager.getConnection("jdbc:hsqldb:file:" + dataSource.getFilePath(), dataSource.getUserName(), dataSource.getPwd());
            } else {
                conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPwd());
            }*/
            if (conn != null) {
                isAvailable = true;
            }
        } catch (Exception e) {
            isAvailable = false;
            log.error("获得数据库连接失败！", e);
        }

        return conn;
    }

    @Override
    public List<DataMap> getResultSet(String sql) {
        List<DataMap> list = new ArrayList<DataMap>();
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
        } catch (Exception e) {
            log.info("SQL语句执行错误： ================================");
            log.info(sql);
            log.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

        return list;
    }

    @Override
    @JSONField(serialize = false)
    public DBLoader getLoader() throws Exception {
        return loader;
    }

    @Override
    public DatabaseType getDatabaseType() throws Exception {
        return dbType;
    }

    @Override
    @JSONField(serialize = false)
    public DBSchema getSchema() throws Exception {
        return currentSchema;
    }

    @Override
    @JSONField(serialize = false)
    public List<DBSchema> getSchemas() throws Exception {
        return schemas;
    }

    @Override
    public void execSqlFile(File sqlFile) throws Exception {
        execSqlScript(UFile.readString(sqlFile), ";");
    }

    @Override
    public void execSqlScript(String script, String splitStr) throws Exception {
        String[] sqls = script.split(splitStr);
        Connection conn = null;
        SqlExecuteEventData eventData = new SqlExecuteEventData();

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            for (String sql : sqls) {
                if(UString.isEmpty(sql)) {
                    continue;
                }
                sql = sql.trim();
                // MySql处理DELIMITER， 忽略此语句
                if (sql.toUpperCase().startsWith("DELIMITER")) {
                    continue;
                }
                eventData = new SqlExecuteEventData();
                eventData.setSql(sql);

                // MySql Drop Index处理
                String temp = sql.toLowerCase();

                if(dbType == DatabaseType.MYSQL) {
                    if(temp.startsWith("drop index ")) {
                        int idx = temp.indexOf("drop index ");
                        int onIndex = temp.indexOf(" on ", idx + 11);
                        String indexName = temp.substring(idx + 11, onIndex).trim();
                        String tableName = temp.substring(onIndex + 4).trim();
                        loader.deleteIndex(tableName, indexName);

                        eventData.setSuccess(true);
                        sqlExecuteSubject.notifyObserver(eventData);

                        continue;
                    }
                }

                if (temp.startsWith("drop table if exists ")) {
                    String tableName = temp.substring("drop table if exists ".length());
                    loader.dropTable(tableName);

                    eventData.setSuccess(true);
                    sqlExecuteSubject.notifyObserver(eventData);

                    continue;
                }
                log.info("SQL = " + sql);
                Statement stmt = conn.createStatement();
                stmt.execute(sql);

                eventData.setSuccess(true);
                sqlExecuteSubject.notifyObserver(eventData);
            }
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            log.error("执行Sql脚本错误：", e);
            eventData.setException(e);
            sqlExecuteSubject.notifyObserver(eventData);
            throw e;
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
    @JSONField(serialize = false)
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

    @Override
    public Subject<SqlExecuteEventData> getSqlExecuteSubject() {
        return sqlExecuteSubject;
    }

    public void setSchemas(List<DBSchema> schemas) {
        this.schemas = schemas;
    }

    public void setCurrentSchema(DBSchema currentSchema) {
        this.currentSchema = currentSchema;
    }
}
