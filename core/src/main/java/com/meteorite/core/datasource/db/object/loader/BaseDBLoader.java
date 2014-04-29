package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.impl.*;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UObject;

import java.util.ArrayList;
import java.util.List;

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
    // 获得Schema Sql语句
    protected abstract String getSchemaSql();
    // 获得Table sql语句
    protected abstract String getTableSql();
    // 获得View Sql语句
    protected abstract String getViewSql();
    // 获得Column Sql语句
    protected abstract String getColumnSql();

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

            result.add(schema);

            // 设置Schema子节点
            List<ITreeNode> children = new ArrayList<>();

            DBObjectImpl tables = new DBObjectImpl("Tables", "表", new ArrayList<ITreeNode>(schema.getTables()));
            tables.setIcon(DBIcons.DBO_TABLES);
            tables.setPresentableText(String.format(" (%s)", schema.getTables().size()));
            tables.setObjectType(DBObjectType.TABLE);

            DBObjectImpl views = new DBObjectImpl("Views", "视图", new ArrayList<ITreeNode>(schema.getViews()));
            views.setIcon(DBIcons.DBO_VIEWS);
            views.setObjectType(DBObjectType.VIEW);
            views.setPresentableText(String.format(" (%s)", schema.getViews().size()));

            children.add(tables);
            children.add(views);

            schema.setChildren(children);
        }
        return result;
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
            result.add(table);

            // 设置Table子节点
            List<ITreeNode> children = new ArrayList<>();

            DBObjectImpl columns = new DBObjectImpl("Columns", "", new ArrayList<ITreeNode>(table.getColumns()));
            columns.setIcon(DBIcons.DBO_COLUMNS);
            columns.setObjectType(DBObjectType.COLUMN);
            columns.setPresentableText(String.format(" (%s)", table.getColumns().size()));

            children.add(columns);
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
        }
        return result;
    }

    @Override
    public List<DBColumn> loadColumns(DBDataset table) throws Exception {
        List<DBColumn> result = new ArrayList<>();
        List<DataMap> list = conn.getResultSet(String.format(getColumnSql(), table.getSchema().getName(), table.getName()));
        for (DataMap map : list) {
            DBColumnImpl column = new DBColumnImpl();
            column.setParent(table);
            column.setName(map.getString("COLUMN_NAME"));
            column.setComment(map.getString("COLUMN_COMMENT"));
            column.setDataType(MetaDataType.getDataType(map.getString("DATA_TYPE_NAME")));
            column.setMaxLength(map.getInt("DATA_LENGTH"));
            column.setPk(map.getBoolean("IS_PRIMARY_KEY"));
            column.setFk(map.getBoolean("IS_FOREIGN_KEY"));

            result.add(column);
        }
        return result;
    }
}
