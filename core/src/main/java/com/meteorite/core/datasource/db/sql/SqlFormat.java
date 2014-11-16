package com.meteorite.core.datasource.db.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcUtils;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlFormat {
    public static final String COL_NAME = "colName";
    public static final String VALUE = "value";
    public static final String VALUE_LENGTH = "valueLength";
    public static final String DB_DATA_TYPE = "dbDataType";
    public static final String MAX_LENGTH = "maxLength";

    private String sql;
    private String dbType;
    private List<DataMap> dataList;
    private DBDataSource dataSource;

    public SqlFormat(String sql, DBDataSource dataSource) throws Exception {
        this.sql = sql.trim();
        this.dataSource = dataSource;
        DatabaseType type = dataSource.getDatabaseType();
        dbType = convertToDruidType(type);
    }

    public static String convertToDruidType(DatabaseType type) {
        switch (type) {
            case SQLSERVER: {
                return JdbcUtils.SQL_SERVER;
            }
            case MYSQL: {
                return JdbcUtils.MYSQL;
            }
            case ORACLE: {
                return JdbcUtils.ORACLE;
            }
            case HSQLDB: {
                return JdbcUtils.HSQL;
            }
        }
        return JdbcUtils.MYSQL;
    }

    public String format(DBSchema schema) throws Exception {
        dataList = new ArrayList<DataMap>();
        // parser得到AST
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);

        List<SQLStatement> stmtList = parser.parseStatementList(); //
        // 将AST通过visitor输出
        for (SQLStatement stmt : stmtList) {
            //stmt.accept(visitor);
            if (stmt instanceof SQLSelectStatement) {
                SQLSelectStatement sstmt = (SQLSelectStatement)stmt;
                SQLSelect sqlselect = sstmt.getSelect();
                SQLSelectQueryBlock query = (SQLSelectQueryBlock)sqlselect.getQuery();


                for(SQLSelectItem item : query.getSelectList()) {
                    DataMap dataMap = new DataMap();
                    String colName = item.toString().split(" ")[0];
                    dataMap.put(COL_NAME, colName);
                    dataMap.put(VALUE, item.getAlias());
                    dataList.add(dataMap);
                }
            } else if (stmt instanceof SQLInsertStatement) {
                SQLInsertStatement insertStatement = (SQLInsertStatement) stmt;
                String tableName = insertStatement.getTableName().getSimpleName();
                DBTable table = schema.getTable(tableName);

                for (int i = 0; i < insertStatement.getColumns().size(); i++) {
                    DataMap dataMap = new DataMap();
                    String colName = insertStatement.getColumns().get(i).toString();
                    dataMap.put(COL_NAME, colName);
                    if (i < insertStatement.getValues().getValues().size()) {
                        String value = trimQuote(insertStatement.getValues().getValues().get(i).toString());
                        dataMap.put(VALUE, value);
                        dataMap.put(VALUE_LENGTH, value.length());
                    } else {
                        dataMap.put(VALUE, "");
                        dataMap.put(VALUE_LENGTH, "0");
                    }
                    DBColumn column = table.getColumn(colName);
                    dataMap.put(DB_DATA_TYPE, column.getDataTypeString());
                    dataMap.put(MAX_LENGTH, column.getMaxLength());

                    dataList.add(dataMap);
                }
            } else if (stmt instanceof SQLUpdateStatement) {
                SQLUpdateStatement updateStatement = (SQLUpdateStatement) stmt;
                String tableName = updateStatement.getTableName().getSimpleName();
                DBTable table = schema.getTable(tableName);

                for (SQLUpdateSetItem item : updateStatement.getItems()) {
                    DataMap dataMap = new DataMap();
                    String colName = item.getColumn().toString();
                    dataMap.put(COL_NAME, colName);
                    String value = trimQuote(item.getValue().toString());
                    dataMap.put(VALUE, value);
                    dataMap.put(VALUE_LENGTH, value.length());

                    DBColumn column = table.getColumn(colName);
                    dataMap.put(DB_DATA_TYPE, column.getDataTypeString());
                    dataMap.put(MAX_LENGTH, column.getMaxLength());

                    dataList.add(dataMap);
                }
            }
        }
        return SQLUtils.format(sql, dbType);
    }

    // 去掉首尾单引号
    private String trimQuote(String value) {
        if (value.startsWith("'")) {
            value = value.substring(1);
        }
        if (value.endsWith("'")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    public List<DataMap> getDataList() {
        return dataList;
    }
}
