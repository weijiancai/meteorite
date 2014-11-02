package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.*;
import com.meteorite.core.datasource.db.object.impl.*;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.eventdata.LoaderEventData;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseDBLoader implements DBLoader {
    protected DBConnectionImpl dbConn;
    private boolean isLoaded;
    private List<Observer> observers;
    private String message;

    public BaseDBLoader(DBConnectionImpl conn) throws Exception {
        this.dbConn = conn;
        observers = new ArrayList<Observer>();
    }

    // 获得User Sql语句
    protected abstract String getUserSql();
    // 获得Privileges语句
    protected abstract String getPrivilegesSql();
    // 获得Charsets语句
    protected abstract String getCharsetsSql();
    // 获得Schema Sql语句
    protected abstract String getSchemaSql();
    // 获得Table sql语句
    protected abstract String getTableSql();
    // 获得View Sql语句
    protected abstract String getViewSql();
    // 获得Column Sql语句
    protected abstract String getColumnSql();
    // 获得Constraints Sql语句
    protected abstract String getConstraintsSql();
    // 获得Indexes Sql语句
    protected abstract String getIndexesSql();
    // 获得Index Sql语句
    protected abstract String getIndexSql(String schema, String tableName, String indexName);
    // 获得Triggers Sql语句
    protected abstract String getTriggersSql();
    // 获得Procedures Sql语句
    protected abstract String getProceduresSql();
    // 获得Functions Sql语句
    protected abstract String getFunctionsSql();
    // 获得Parameters Sql语句
    protected abstract String getParametersSql();
    // 获得FK Constraints Columns Sql语句
    protected abstract String getFKConstraintsColumnsSql();

    private ITreeNode navTree;

    @Override
    public void load() throws Exception {
        if (isLoaded) {
            return;
        }
        if (!dbConn.isAvailable()) {
            return;
        }
        navTree = dbConn.getDataSource().getNavTree();
        List<DBSchema> schemas = loadSchemas();
        DBObjectList dbSchemas = new DBObjectList("Schemas", DBIcons.DBO_SCHEMAS, new ArrayList<ITreeNode>(schemas));
        dbSchemas.setParent(navTree);

        List<DBUser> users = loadUsers();
        DBObjectList dbUsers = new DBObjectList("Users", DBIcons.DBO_USERS, new ArrayList<ITreeNode>(users));
        dbUsers.setParent(navTree);

        List<DBObject> privileges = loadPrivileges();
        DBObjectList dbPrivileges = new DBObjectList("Privileges", DBIcons.DBO_PRIVILEGES, new ArrayList<ITreeNode>(privileges));
        dbPrivileges.setParent(navTree);

        List<DBObject> charsetList = loadCharsets();
        DBObjectList dbCharsets = new DBObjectList("Charset", null, new ArrayList<ITreeNode>(charsetList));
        dbCharsets.setParent(navTree);

        List<ITreeNode> list = new ArrayList<ITreeNode>();
        list.add(dbSchemas);
        list.add(dbUsers);
        list.add(dbPrivileges);
        list.add(dbCharsets);

        navTree.getChildren().addAll(list);
        dbConn.setSchemas(schemas);
        initCurrentSchema(schemas);

        isLoaded = true;
    }

    private void initCurrentSchema(List<DBSchema> schemas) throws Exception {
        Connection conn = null;
        try {
            conn = dbConn.getConnection();
            for (DBSchema schema : schemas) {
                if (schema.getName().equalsIgnoreCase(conn.getCatalog())) {
                    dbConn.setCurrentSchema(schema);
                    break;
                }
            }
        } finally {
            ConnectionUtil.closeConnection(conn);
        }
    }

    @Override
    public List<DBUser> loadUsers() throws Exception {
        List<DBUser> result = new ArrayList<DBUser>();
        List<DataMap> list = dbConn.getResultSet(getUserSql());
        for (DataMap map : list) {
            DBUserImpl user = new DBUserImpl();
            user.setParent(navTree);
            user.setDataSource(dbConn.getDataSource());
            user.setName(UObject.toString(map.get("USER_NAME")));
            user.setComment(UObject.toString(map.get("USER_NAME")));
            user.setObjectType(DBObjectType.USER);

            result.add(user);
            notifyMessage(String.format("加载用户：" + user.getName()));
        }

        return result;
    }

    @Override
    public List<DBObject> loadPrivileges() {
        List<DBObject> result = new ArrayList<DBObject>();
        List<DataMap> list = dbConn.getResultSet(getPrivilegesSql());
        for (DataMap map : list) {
            DBObjectImpl privilege = new DBObjectImpl();
            privilege.setParent(navTree);
            privilege.setName(UObject.toString(map.get("PRIVILEGE_NAME")));
            privilege.setComment(UObject.toString(map.get("PRIVILEGE_NAME")));
            privilege.setObjectType(DBObjectType.PRIVILEGE);

            result.add(privilege);
            notifyMessage(String.format("加载授权：" + privilege.getName()));
        }

        return result;
    }

    @Override
    public List<DBObject> loadCharsets() {
        List<DBObject> result = new ArrayList<DBObject>();
        List<DataMap> list = dbConn.getResultSet(getCharsetsSql());
        for (DataMap map : list) {
            DBObjectImpl charset = new DBObjectImpl();
            charset.setParent(navTree);
            charset.setName(UObject.toString(map.get("CHARSET_NAME")));
            charset.setComment(UObject.toString(map.get("CHARSET_NAME")));
            charset.setObjectType(DBObjectType.CHARSET);

            result.add(charset);
            notifyMessage(String.format("加载字符集：" + charset.getName()));
        }

        return result;
    }

    @Override
    public List<DBSchema> loadSchemas() throws Exception {
        List<DBSchema> result = new ArrayList<DBSchema>();
        List<DataMap> list = dbConn.getResultSet(getSchemaSql());
        for (DataMap map : list) {
            String schemaName = map.getString("SCHEMA_NAME");
            if (!dbConn.getSchema().getName().equals(schemaName)) {
                continue;
            }
            notifyMessage(String.format("加载Schema：" + schemaName));

            DBSchemaImpl schema = new DBSchemaImpl();
            schema.setDataSource(dbConn.getDataSource());
            schema.setParent(navTree);
            schema.setName(schemaName);
            schema.setComment(schema.getName());
            // 加载Table
            schema.setTables(loadTables(schema));
            // 加载View
            schema.setViews(loadViews(schema));
            // 加载Index
            schema.setIndexes(loadIndexes(schema));
            // 加载Trigger
            schema.setTriggers(loadTriggers(schema));
            // 加载Procedure
            schema.setProcedures(loadProcedures(schema));
            // 加载Function
            schema.setFunctions(loadFunctions(schema));
            // 加载参数
            loadParameters(schema);
            // 加载外键约束
            loadFkConstraints(schema);

            result.add(schema);

            // 设置Schema子节点
            List<ITreeNode> children = new ArrayList<ITreeNode>();
            // 加载表
            DBObjectList tables = new DBObjectList("Tables", DBIcons.DBO_TABLES, new ArrayList<ITreeNode>(schema.getTables()));
            // 加载视图
            DBObjectList views = new DBObjectList("Views", DBIcons.DBO_VIEWS, new ArrayList<ITreeNode>(schema.getViews()));
            // 加载索引
            DBObjectList indexes = new DBObjectList("Indexes", DBIcons.DBO_INDEXES, new ArrayList<ITreeNode>(schema.getIndexes()));
            // 加载触发器
            DBObjectList triggers = new DBObjectList("Triggers", DBIcons.DBO_TRIGGERS, new ArrayList<ITreeNode>(schema.getTriggers()));
            // 加载存储过程
            DBObjectList procedures = new DBObjectList("Procedures", DBIcons.DBO_PROCEDURES, new ArrayList<ITreeNode>(schema.getProcedures()));
            // 加载函数
            DBObjectList functions = new DBObjectList("Functions", DBIcons.DBO_FUNCTIONS, new ArrayList<ITreeNode>(schema.getFunctions()));

            children.add(tables);
            children.add(views);
            children.add(indexes);
            children.add(triggers);
            children.add(procedures);
            children.add(functions);

            schema.setChildren(children);

            // 加载表的索引、触发器
            for (DBTable table : schema.getTables()) {
                // 索引
                List<DBIndex> indexList = table.getIndexes();
                DBObjectList indexObject = new DBObjectList("Indexes", DBIcons.DBO_INDEXES, new ArrayList<ITreeNode>(indexList));

                table.getChildren().add(indexObject);
                // 触发器
                List<DBTrigger> triggerList = table.getTriggers();
                DBObjectList triggerObject = new DBObjectList("Triggers", DBIcons.DBO_TRIGGERS, new ArrayList<ITreeNode>(triggerList));

                table.getChildren().add(triggerObject);
            }
        }
        return result;
    }

    @Override
    public List<DBIndex> loadIndexes(DBSchema schema) throws Exception {
        List<DBIndex> result = new ArrayList<DBIndex>();
        List<DataMap> list = dbConn.getResultSet(String.format(getIndexesSql(), schema.getName()));
        Map<String, DBIndexImpl> indexMap = new HashMap<String, DBIndexImpl>();

        for (DataMap map : list) {
            String indexName = map.getString("INDEX_NAME");
            DBIndexImpl index = indexMap.get(indexName);
            if (index == null) {
                index = new DBIndexImpl();
                index.setSchema(schema);
                index.setName(indexName);
                index.setAsc(map.getBoolean("IS_ASC"));
                index.setUnique(map.getBoolean("IS_UNIQUE"));
                index.setTableName(map.getString("TABLE_NAME"));
                index.setColumns(new ArrayList<DBColumn>());

                DBTable table = schema.getTable(map.getString("TABLE_NAME"));
                if (table != null) {
                    table.getIndexes().add(index);
                    index.setTable(table);
                }

                indexMap.put(indexName, index);

                result.add(index);
                notifyMessage(String.format("加载Index：" + index.getName()));
            }
            if (index.getTable() != null) {
                index.getColumns().add(index.getTable().getColumn(map.getString("COLUMN_NAME")));
            }

            index.getColumnNames().add(map.getString("COLUMN_NAME"));
        }

        return result;
    }

    @Override
    public List<DBTrigger> loadTriggers(DBSchema schema) {
        List<DBTrigger> result = new ArrayList<DBTrigger>();
        List<DataMap> list = dbConn.getResultSet(String.format(getTriggersSql(), schema.getName()));

        for (DataMap map : list) {
            DBTriggerImpl trigger = new DBTriggerImpl();
            trigger.setSchema(schema);
            trigger.setName(map.getString("TRIGGER_NAME"));
            trigger.setForEachRow(map.getBoolean("IS_FOR_EACH_ROW"));
            trigger.setTriggerType(DBTriggerType.convert(map.getString("TRIGGER_TYPE")));
            trigger.setTriggerEvents(DBTriggerEvent.convertToList(map.getString("TRIGGERING_EVENT")));

            DBTable table = schema.getTable(map.getString("DATASET_NAME"));
            table.getTriggers().add(trigger);
            trigger.setTable(table);

            result.add(trigger);
            notifyMessage(String.format("加载Trigger：" + trigger.getName()));
        }

        return result;
    }

    @Override
    public List<DBProcedure> loadProcedures(DBSchema schema) {
        List<DBProcedure> result = new ArrayList<DBProcedure>();
        List<DataMap> list = dbConn.getResultSet(String.format(getProceduresSql(), schema.getName()));

        for (DataMap map : list) {
            DBProcedureImpl procedure = new DBProcedureImpl();
            procedure.setSchema(schema);
            procedure.setName(map.getString("PROCEDURE_NAME"));

            result.add(procedure);
            notifyMessage(String.format("加载Procedure：" + procedure.getName()));
        }

        return result;
    }

    @Override
    public List<DBFunction> loadFunctions(DBSchema schema) {
        List<DBFunction> result = new ArrayList<DBFunction>();
        List<DataMap> list = dbConn.getResultSet(String.format(getFunctionsSql(), schema.getName()));

        for (DataMap map : list) {
            DBFunctionImpl function = new DBFunctionImpl();
            function.setSchema(schema);
            function.setName(map.getString("FUNCTION_NAME"));

            result.add(function);
            notifyMessage(String.format("加载Function：" + function.getName()));
        }

        return result;
    }

    public void loadParameters(DBSchema schema) {
        List<DataMap> list = dbConn.getResultSet(String.format(getParametersSql(), schema.getName()));
        for (DataMap map : list) {
            String methodName = map.getString("METHOD_NAME");
            DBMethodType type = DBMethodType.valueOf(map.getString("METHOD_TYPE"));
            DBMethod method;
            if (type == DBMethodType.FUNCTION) {
                method = schema.getFunction(methodName);
            } else if (type == DBMethodType.PROCEDURE) {
                method = schema.getProcedure(methodName);
            } else {
                return;
            }

            List<ITreeNode> children = method.getChildren();
            if (children.size() == 0) {
                children.add(new DBObjectList("Arguments", DBIcons.DBO_ARGUMENTS, null));
            }
            DBArgumentImpl argument = new DBArgumentImpl();
            argument.setSchema(schema);
            argument.setMethod(method);
            argument.setName(map.getString("ARGUMENT_NAME"));
            argument.setPosition(map.getInt("POSITION"));
            argument.setSequence(map.getInt("SEQUENCE"));
            String inOut = map.getString("IN_OUT");
            if (inOut.equals("INOUT")) {
                argument.setInput(true);
                argument.setOutput(true);

            } else if (inOut.equals("IN")) {
                argument.setInput(true);

            } else if (inOut.equals("OUT")) {
                if (UString.isEmpty(argument.getName())) {
                    argument.setName("out");
                }
                argument.setOutput(true);

            }

            DBDataType dataType = new DBDataType();
            dataType.setLength(map.getLong("DATA_LENGTH"));
            dataType.setPrecision(map.getInt("DATA_PRECISION"));
            dataType.setScale(map.getInt("DATA_SCALE"));
            dataType.setTypeName(map.getString("DATA_TYPE_NAME"));
            argument.setDataType(dataType);

            children.get(0).getChildren().add(argument);
        }
    }

    @Override
    public List<DBTable> loadTables(DBSchema schema) throws Exception {
        List<DBTable> result = new ArrayList<DBTable>();
        List<DataMap> list = dbConn.getResultSet(String.format(getTableSql(), schema.getName()));
        for (DataMap map : list) {
            DBTableImpl table = new DBTableImpl();
            table.setSchema(schema);
            table.setParent(schema);
            table.setDataSource(dbConn.getDataSource());
            table.setName(UObject.toString(map.get("TABLE_NAME")));
            table.setComment(UObject.toString(map.get("TABLE_COMMENT")));
            // 加载列
            table.setColumns(loadColumns(table));
            // 加载约束
            table.setConstraints(loadConstraint(table));
            result.add(table);

            // 设置Table子节点
            List<ITreeNode> children = new ArrayList<ITreeNode>();
            // 列
            DBObjectList columns = new DBObjectList("Columns", DBIcons.DBO_COLUMNS, new ArrayList<ITreeNode>(table.getColumns()));
            // 约束
            DBObjectList constraints = new DBObjectList("Constraints", DBIcons.DBO_CONSTRAINTS, new ArrayList<ITreeNode>(table.getConstraints()));

            children.add(columns);
            children.add(constraints);
            table.setChildren(children);
            notifyMessage(String.format("加载Table：" + table.getName()));
        }

        return result;
    }


    @Override
    public List<DBView> loadViews(DBSchema schema) throws Exception {
        List<DBView> result = new ArrayList<DBView>();
        List<DataMap> list = dbConn.getResultSet(String.format(getViewSql(), schema.getName()));
        for (DataMap map : list) {
            DBViewImpl view = new DBViewImpl();
            view.setSchema(schema);
            view.setParent(schema);
            view.setName(UObject.toString(map.get("VIEW_NAME")));
            view.setComment(UObject.toString(map.get("VIEW_COMMENT")));
            view.setSchema(schema);
            // 加载列
            view.setColumns(loadColumns(view));
            result.add(view);

            // 设置View子节点
            List<ITreeNode> children = new ArrayList<ITreeNode>();
            // 列
            DBObjectList columns = new DBObjectList("Columns", DBIcons.DBO_COLUMNS, new ArrayList<ITreeNode>(view.getColumns()));

            children.add(columns);
            view.setChildren(children);
            notifyMessage(String.format("加载View：" + view.getName()));
        }
        return result;
    }

    @Override
    public List<DBColumn> loadColumns(DBDataset table) {
        List<DBColumn> result = new ArrayList<DBColumn>();
        List<DataMap> list = dbConn.getResultSet(String.format(getColumnSql(), table.getSchema().getName(), table.getName()));
        for (DataMap map : list) {
            DBColumnImpl column = new DBColumnImpl();
            column.setSchema(table.getSchema());
            column.setParent(table);
            column.setDataset(table);
            column.setName(map.getString("COLUMN_NAME"));
            column.setComment(map.getString("COLUMN_COMMENT"));
            column.setDbDataType(map.getString("DATA_TYPE_NAME"));
            column.setMaxLength(map.getInt("DATA_LENGTH"));
            column.setPk(map.getBoolean("IS_PRIMARY_KEY"));
            column.setFk(map.getBoolean("IS_FOREIGN_KEY"));
            column.setPrecision(map.getInt("DATA_PRECISION"));
            column.setScale(map.getInt("DATA_SCALE"));
            column.setNullable(map.getBoolean("IS_NULLABLE"));

            result.add(column);
        }
        return result;
    }

    public List<DBConstraint> loadConstraint(DBDataset dataset) {
        DBSchemaImpl schema = (DBSchemaImpl) dataset.getSchema();
        List<DBConstraint> result = new ArrayList<DBConstraint>();
        List<DataMap> list = dbConn.getResultSet(String.format(getConstraintsSql(), dataset.getSchema().getName(), dataset.getName()));
        for (DataMap map : list) {
            DBConstraintImpl constraint = new DBConstraintImpl();
            constraint.setSchema(dataset.getSchema());
            constraint.setParent(dataset);
            constraint.setName(map.getString("CONSTRAINT_NAME"));
            constraint.setConstraintType(DBConstraintType.convert(map.getString("CONSTRAINT_TYPE")));

            schema.addConstraints(constraint);

            result.add(constraint);
            notifyMessage(String.format("加载Constraint：" + constraint.getName()));
        }
        return result;
    }

    public List<DBConstraint> loadFkConstraints(DBSchema schema) throws Exception {
        List<DBConstraint> result = new ArrayList<DBConstraint>();

        List<DataMap> list = dbConn.getResultSet(String.format(getFKConstraintsColumnsSql(), schema.getName()));
        for (DataMap map : list) {
            String constraintName = map.getString("constraint_name");
            String fkTableName = map.getString("table_name");
            String fkColName = map.getString("column_name");
            String pkTableName = map.getString("referenced_table_name");
            String pkColName = map.getString("referenced_column_name");
            DBTable fkTable = schema.getTable(fkTableName);
            DBConstraintImpl constraint;

            if (fkTable != null) {
                DBColumnImpl fkColumn = (DBColumnImpl) fkTable.getColumn(fkColName);
                DBTable pkTable = schema.getTable(pkTableName);
                DBColumnImpl pkColumn = (DBColumnImpl) pkTable.getColumn(pkColName);
                fkColumn.setRefColumn(pkColumn);
                constraint = (DBConstraintImpl) schema.getConstraint(constraintName);
                constraint.setPrimaryKeyTable(pkTable);
                constraint.setForeignKeyTable(fkTable);
                constraint.getColumns().add(fkColumn);
            } else {
                constraint = new DBConstraintImpl();
            }

            constraint.setName(constraintName);
            constraint.setPkTableName(pkTableName);
            constraint.setPkColumnName(pkColName);
            constraint.setFkTableName(fkTableName);
            constraint.setFkColumnName(fkColName);

            result.add(constraint);
            notifyMessage(String.format("加载FKConstraint：" + constraint.getName()));
        }

        return result;
    }

    @Override
    public DBTable getTable(String tableName) throws Exception {
        Connection connection = dbConn.getConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), null);
            if (rs.next()) {
                DBTableImpl table = new DBTableImpl();
                table.setDataSource(dbConn.getDataSource());
                table.setName(UObject.toString(rs.getString("TABLE_NAME")));
                table.setComment(UObject.toString(rs.getString("REMARKS")));
                table.setSchema(dbConn.getSchema());

                return table;
            }
        } finally {
            ConnectionUtil.closeConnection(connection);
        }

        return null;
    }

    @Override
    public DBColumn getColumn(String tableName, String columnName) throws Exception {
        Connection connection = dbConn.getConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, columnName);
            if (rs.next()) {
                DBColumnImpl column = new DBColumnImpl();
                column.setDataSource(dbConn.getDataSource());
                column.setName(UObject.toString(rs.getString("COLUMN_NAME")));
                column.setComment(UObject.toString(rs.getString("REMARKS")));
                String typeName = rs.getString("TYPE_NAME");
                String columnSize = rs.getString("COLUMN_SIZE");
                column.setDataTypeString(String.format("%s(%s)", typeName, columnSize));
                column.setNullable("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")));
                return column;
            }
        } finally {
            ConnectionUtil.closeConnection(connection);
        }
        return null;
    }

    @Override
    public void deleteIndex(String tableName, String indexName) throws Exception {
        Connection connection = dbConn.getConnection();
        JdbcTemplate template = new JdbcTemplate(connection);
        try {
            String sql = getIndexSql(dbConn.getSchema().getName(), tableName, indexName);
            List<DataMap> list = template.queryForList(sql);
            if(list.size() == 1) {
                template.update(String.format("drop index %s on %s", indexName, tableName));
            }
        } finally {
            template.close();
        }
    }


    @Override
    public void dropTable(String tableName) throws Exception {
        DBTable table = getTable(tableName);
        if (table == null) {
            return;
        }

        String sql;
        JdbcTemplate template = new JdbcTemplate(dbConn.getDataSource());
        try {
            for (DBConstraint constraint : getExportedKeys(tableName)) {
                sql = String.format("ALTER TABLE %s DROP FOREIGN KEY %s", constraint.getFkTableName(), constraint.getFkName());
                template.update(sql);
            }

            template.update(String.format("drop table %s", tableName));
        } finally {
            template.close();
        }
    }

    @Override
    public void dropForeignKey(String tableName, String referenceName) throws Exception {
        JdbcTemplate template = new JdbcTemplate(dbConn.getDataSource());
        try {
            String sql = String.format("ALTER TABLE %s DROP FOREIGN KEY %s", tableName, referenceName);
            template.update(sql);
        } finally {
            template.close();
        }
    }

    @Override
    public List<DBConstraint> getExportedKeys(String table) throws Exception {
        List<DBConstraint> result = new ArrayList<DBConstraint>();

        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        Connection conn = dataSource.getDbConnection().getConnection();
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rs = dbMetaData.getExportedKeys(conn.getCatalog(), conn.getSchema(), table);
            while (rs.next()) {
                DBConstraintImpl constraint = new DBConstraintImpl();
                constraint.setPkCatalog(rs.getString("PKTABLE_CAT"));
                constraint.setPkSchema(rs.getString("PKTABLE_SCHEM"));
                constraint.setPkTableName(rs.getString("PKTABLE_NAME"));
                constraint.setPkColumnName(rs.getString("PKCOLUMN_NAME"));
                constraint.setFkCatalog(rs.getString("FKTABLE_CAT"));
                constraint.setFkSchema(rs.getString("FKTABLE_SCHEM"));
                constraint.setFkTableName(rs.getString("FKTABLE_NAME"));
                constraint.setFkColumnName(rs.getString("FKCOLUMN_NAME"));
                constraint.setKeySeq(rs.getInt("KEY_SEQ"));
                constraint.setUpdateRule(rs.getString("UPDATE_RULE"));
                constraint.setDeleteRule(rs.getString("DELETE_RULE"));
                constraint.setFkName(rs.getString("FK_NAME"));
                constraint.setPkName(rs.getString("PK_NAME"));

                result.add(constraint);
            }
            rs.close();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }

        return result;
    }

    @Override
    public void updateColumnNullable(String table, String column, boolean nullable) throws Exception {
        DBColumn dbColumn = getColumn(table, column);
        if (dbColumn == null) {
            throw new Exception(String.format("表【%s】中没有此列【%s】", table, column));
        }
        if ((nullable && dbColumn.isNullable()) || (!nullable && !dbColumn.isNullable())) { // 如果nullable没有改变，数据库不需要更新
            return;
        }
        // 更新列
        String sql = String.format("ALTER TABLE %s MODIFY %s %s %s", table, column, dbColumn.getDataTypeString(), nullable ? "NULL" : "NOT NULL");
        JdbcTemplate template = new JdbcTemplate();
        try {
            template.update(sql);
        } finally {
            template.close();
        }
    }

    @Override
    public void renameTable(String oldName, String newName) throws Exception {
        String sql = String.format("ALTER TABLE %s RENAME TO %s", oldName, newName);
        JdbcTemplate template = new JdbcTemplate();
        try {
            template.update(sql);
        } finally {
            template.close();
        }
    }

    private void notifyMessage(String message) {
        this.message = message;
        dbConn.getDataSource().getLoaderSubject().notifyObserver(new LoaderEventData(message));
    }
}
