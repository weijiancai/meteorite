package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.*;
import com.meteorite.core.datasource.db.object.impl.*;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseDBLoader implements DBLoader {
    protected DBConnection conn;

    public BaseDBLoader(DBConnection conn) throws Exception {
        this.conn = conn;
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

    @Override
    public void load() {

    }

    @Override
    public List<DBUser> loadUsers() throws Exception {
        List<DBUser> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(getUserSql());
        for (DataMap map : list) {
            DBUserImpl user = new DBUserImpl();
            user.setName(UObject.toString(map.get("USER_NAME")));
            user.setComment(UObject.toString(map.get("USER_NAME")));

            result.add(user);
        }

        return result;
    }

    @Override
    public List<DBObject> loadPrivileges() throws Exception {
        List<DBObject> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(getPrivilegesSql());
        for (DataMap map : list) {
            DBObjectImpl privilege = new DBObjectImpl();
            privilege.setName(UObject.toString(map.get("PRIVILEGE_NAME")));
            privilege.setComment(UObject.toString(map.get("PRIVILEGE_NAME")));
            privilege.setObjectType(DBObjectType.PRIVILEGE);

            result.add(privilege);
        }

        return result;
    }

    @Override
    public List<DBObject> loadCharsets() throws Exception {
        List<DBObject> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(getCharsetsSql());
        for (DataMap map : list) {
            DBObjectImpl privilege = new DBObjectImpl();
            privilege.setName(UObject.toString(map.get("CHARSET_NAME")));
            privilege.setComment(UObject.toString(map.get("CHARSET_NAME")));
            privilege.setObjectType(DBObjectType.CHARSET);

            result.add(privilege);
        }

        return result;
    }

    @Override
    public List<DBSchema> loadSchemas() throws Exception {
        List<DBSchema> result = new ArrayList<DBSchema>();
        List<DataMap> list = conn.getResultSet(getSchemaSql());
        for (DataMap map : list) {
            DBSchemaImpl schema = new DBSchemaImpl();
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

            result.add(schema);

            // 设置Schema子节点
            List<ITreeNode> children = new ArrayList<>();
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
        List<DBIndex> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getIndexesSql(), schema.getName()));
        Map<String, DBIndexImpl> indexMap = new HashMap<>();

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
                table.getIndexes().add(index);
                index.setTable(table);

                indexMap.put(indexName, index);
            }
            index.getColumns().add(index.getTable().getColumn(map.getString("COLUMN_NAME")));

            result.add(index);
        }

        return result;
    }

    @Override
    public List<DBTrigger> loadTriggers(DBSchema schema) throws Exception {
        List<DBTrigger> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getTriggersSql(), schema.getName()));

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
    public List<DBProcedure> loadProcedures(DBSchema schema) throws Exception {
        List<DBProcedure> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getProceduresSql(), schema.getName()));

        for (DataMap map : list) {
            DBProcedureImpl procedure = new DBProcedureImpl();
            procedure.setSchema(schema);
            procedure.setName(map.getString("PROCEDURE_NAME"));

            result.add(procedure);
        }

        return result;
    }

    @Override
    public List<DBFunction> loadFunctions(DBSchema schema) throws Exception {
        List<DBFunction> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getFunctionsSql(), schema.getName()));

        for (DataMap map : list) {
            DBFunctionImpl function = new DBFunctionImpl();
            function.setSchema(schema);
            function.setName(map.getString("FUNCTION_NAME"));

            result.add(function);
        }

        return result;
    }

    public void loadParameters(DBSchema schema) throws Exception {
        List<DataMap> list = conn.getResultSet(String.format(getParametersSql(), schema.getName()));
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
            switch (inOut) {
                case "INOUT":
                    argument.setInput(true);
                    argument.setOutput(true);
                    break;
                case "IN":
                    argument.setInput(true);
                    break;
                case "OUT":
                    argument.setOutput(true);
                    break;
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
        List<DBTable> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getTableSql(), schema.getName()));
        for (DataMap map : list) {
            DBTableImpl table = new DBTableImpl();
            table.setParent(schema);
            table.setName(UObject.toString(map.get("TABLE_NAME")));
            table.setComment(UObject.toString(map.get("TABLE_COMMENT")));
            table.setSchema(schema);
            // 加载列
            table.setColumns(loadColumns(table));
            // 加载约束
            table.setConstraints(loadConstraint(table));
            result.add(table);

            // 设置Table子节点
            List<ITreeNode> children = new ArrayList<>();
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
    public List<DBView> loadViews(DBSchema schema) throws Exception {
        List<DBView> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getViewSql(), schema.getName()));
        for (DataMap map : list) {
            DBViewImpl view = new DBViewImpl();
            view.setParent(schema);
            view.setName(UObject.toString(map.get("VIEW_NAME")));
            view.setComment(UObject.toString(map.get("VIEW_COMMENT")));
            view.setSchema(schema);
            // 加载列
            view.setColumns(loadColumns(view));
            result.add(view);

            // 设置View子节点
            List<ITreeNode> children = new ArrayList<>();
            // 列
            DBObjectList columns = new DBObjectList("Columns", DBIcons.DBO_COLUMNS, new ArrayList<ITreeNode>(view.getColumns()));

            children.add(columns);
            view.setChildren(children);
        }
        return result;
    }

    @Override
    public List<DBColumn> loadColumns(DBDataset table) throws Exception {
        List<DBColumn> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getColumnSql(), table.getSchema().getName(), table.getName()));
        for (DataMap map : list) {
            DBColumnImpl column = new DBColumnImpl();
            column.setSchema(table.getSchema());
            column.setParent(table);
            column.setName(map.getString("COLUMN_NAME"));
            column.setComment(map.getString("COLUMN_COMMENT"));
            column.setDataType(MetaDataType.getDataType(map.getString("DATA_TYPE_NAME")));
            column.setDbDataType(map.getString("DATA_TYPE_NAME"));
            column.setMaxLength(map.getInt("DATA_LENGTH"));
            column.setPk(map.getBoolean("IS_PRIMARY_KEY"));
            column.setFk(map.getBoolean("IS_FOREIGN_KEY"));
            column.setPrecision(map.getInt("DATA_PRECISION"));
            column.setScale(map.getInt("DATA_SCALE"));

            result.add(column);
        }
        return result;
    }

    public List<DBConstraint> loadConstraint(DBDataset dataset) throws Exception {
        List<DBConstraint> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getConstraintsSql(), dataset.getSchema().getName(), dataset.getName()));
        for (DataMap map : list) {
            DBConstraintImpl constraint = new DBConstraintImpl();
            constraint.setSchema(dataset.getSchema());
            constraint.setParent(dataset);
            constraint.setName(map.getString("CONSTRAINT_NAME"));
            constraint.setConstraintType(DBConstraintType.convert(map.getString("CONSTRAINT_TYPE")));

            result.add(constraint);
        }
        return result;
    }
}
