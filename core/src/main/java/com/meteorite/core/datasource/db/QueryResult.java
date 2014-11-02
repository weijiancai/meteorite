package com.meteorite.core.datasource.db;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.datasource.db.sql.SqlFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since  1.0.0
 */
public class QueryResult<T> implements Serializable {
    private int total;
    private int pageRows = 15;
    private List<T> rows = new ArrayList<T>();
    private QueryBuilder queryBuilder;
    private DBDataSource dataSource;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows <= 0 ? 15 : pageRows;
    }

    public int getPageCount() {
        int mode = total % pageRows;
        return total / pageRows + (mode > 0 ? 1 : 0);
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public DBDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DBDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void parseSql() throws Exception {
        String sql = queryBuilder.sql().getSql();
        String dbType = SqlFormat.convertToDruidType(dataSource.getDatabaseType());

        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
        List<SQLStatement> stmtList = parser.parseStatementList(); //
        List<String> tableNameList = new ArrayList<String>();
        List<String> colNameList = new ArrayList<String>();
        // 将AST通过visitor输出
        for (SQLStatement stmt : stmtList) {
            if (stmt instanceof SQLSelectStatement) {
                SQLSelectStatement sstmt = (SQLSelectStatement)stmt;
                SQLSelect sqlselect = sstmt.getSelect();
                SQLSelectQueryBlock query = (SQLSelectQueryBlock)sqlselect.getQuery();
                String from = query.getFrom().toString();
                for (String table : from.split(",")) {
                    tableNameList.add(table.split(" ")[0]);
                }
                for(SQLSelectItem item : query.getSelectList()) {
                    DataMap dataMap = new DataMap();
                    String colName = item.toString().split(" ")[0];
                    colNameList.add(colName);
                }
                System.out.println("===" + query.getFrom());

                /*SQLSelectStatement sqlSelectStatement = (SQLSelectStatement) stmt;
                SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectStatement.getSelect().getQuery();
                String tableName = sqlSelectQueryBlock.getFrom().getAlias();
                VirtualResource tableVR = dataSource.findResourceByPath("/table/" + tableName);
                DBTable table = (DBTable) tableVR.getOriginalObject();*/
            }
        }
    }

    public static void main(String[] args) throws Exception {
        QueryResult<DataMap> result = new QueryResult<DataMap>();
        String sql = "SELECT *\n" +
                "FROM mu_view,mu_view_prop\n" +
                "WHERE 1 = 1\n" +
                "\tAND mu_view.id = mu_view_prop.view_id";
        QueryBuilder builder = QueryBuilder.create(sql, DatabaseType.MYSQL);
        result.setQueryBuilder(builder);
        result.setDataSource(DataSourceManager.getSysDataSource());
        result.parseSql();
    }
}
