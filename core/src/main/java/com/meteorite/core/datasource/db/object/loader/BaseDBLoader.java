package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.*;
import com.meteorite.core.datasource.db.object.impl.*;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;

import java.sql.Connection;
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

    public BaseDBLoader(DBConnectionImpl conn) throws Exception {
        this.dbConn = conn;
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
    public List<DBUser> loadUsers() {
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
        }

        return result;
    }

    @Override
    public List<DBObject> loadCharsets() {
        List<DBObject> result = new ArrayList<DBObject>();
        List<DataMap> list = dbConn.getResultSet(getCharsetsSql());
        for (DataMap map : list) {
            DBObjectImpl privilege = new DBObjectImpl();
            privilege.setParent(navTree);
            privilege.setName(UObject.toString(map.get("CHARSET_NAME")));
            privilege.setComment(UObject.toString(map.get("CHARSET_NAME")));
            privilege.setObjectType(DBObjectType.CHARSET);

            result.add(privilege);
        }

        return result;
    }

    @Override
    public List<DBSchema> loadSchemas() {
        List<DBSchema> result = new ArrayList<DBSchema>();
        List<DataMap> list = dbConn.getResultSet(getSchemaSql());
        for (DataMap map : list) {
            DBSchemaImpl schema = new DBSchemaImpl();
            schema.setDataSource(dbConn.getDataSource());
            schema.setParent(navTree);
            schema.setName(UObject.toString(map.get("SCHEMA_NAME")));
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
    public List<DBIndex> loadIndexes(DBSchema schema) {
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
                index.setColumns(new ArrayList<DBColumn>());

                DBTable table = schema.getTable(map.getString("TABLE_NAME"));
                if (table != null) {
                    table.getIndexes().add(index);
                    index.setTable(table);
                }

                indexMap.put(indexName, index);
            }
            if (index.getTable() != null) {
                index.getColumns().add(index.getTable().getColumn(map.getString("COLUMN_NAME")));
            }

            result.add(index);
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
    public List<DBTable> loadTables(DBSchema schema) {
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
        }

        return result;
    }


    @Override
    public List<DBView> loadViews(DBSchema schema) {
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
            column.setDataType(MetaDataType.getDataType(map.getString("DATA_TYPE_NAME")));
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
        }
        return result;
    }

    public void loadFkConstraints(DBSchema schema) {
        List<DataMap> list = dbConn.getResultSet(String.format(getFKConstraintsColumnsSql(), schema.getName()));
        for (DataMap map : list) {
            String constraintName = map.getString("constraint_name");
            String tableName = map.getString("table_name");
            String colName = map.getString("column_name");
            String fkTableName = map.getString("referenced_table_name");
            String fkColName = map.getString("referenced_column_name");
            DBTable table = schema.getTable(tableName);
            DBColumnImpl column = (DBColumnImpl) table.getColumn(colName);
            DBTable fkTable = schema.getTable(fkTableName);
            DBColumnImpl fkColumn = (DBColumnImpl) fkTable.getColumn(fkColName);
            fkColumn.setRefColumn(column);
            DBConstraintImpl constraint = (DBConstraintImpl) schema.getConstraint(constraintName);
            constraint.setPrimaryKeyTable(table);
            constraint.setForeignKeyTable(fkTable);
            constraint.getColumns().add(fkColumn);
        }
    }
}
