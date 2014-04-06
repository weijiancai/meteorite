package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.impl.DBColumnImpl;
import com.meteorite.core.datasource.db.object.impl.DBSchemaImpl;
import com.meteorite.core.datasource.db.object.impl.DBTableImpl;
import com.meteorite.core.datasource.db.object.impl.DBViewImpl;
import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.meta.MetaDataType;
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

    // 获得Schema Sql语句
    protected abstract String getSchemaSql();
    // 获得Table sql语句
    protected abstract String getTableSql();
    // 获得View Sql语句
    protected abstract String getViewSql();
    // 获得Column Sql语句
    protected abstract String getColumnSql();

    @Override
    public List<DBSchema> loadSchemas() throws Exception {
        List<DBSchema> result = new ArrayList<DBSchema>();
        List<DBResult> list = conn.getResultSet(getSchemaSql());
        for (DBResult map : list) {
            DBSchemaImpl schema = new DBSchemaImpl();
            schema.setName(UObject.toString(map.get("SCHEMA_NAME")));
            schema.setComment(schema.getName());
            // 加载Table
            schema.setTables(loadTables(schema));
            // 加载View
            schema.setViews(loadViews(schema));

            result.add(schema);
        }
        return result;
    }

    @Override
    public List<DBTable> loadTables(DBSchema schema) throws Exception {
        List<DBTable> result = new ArrayList<>();
        List<DBResult> list = conn.getResultSet(String.format(getTableSql(), schema.getName()));
        for (DBResult map : list) {
            DBTableImpl table = new DBTableImpl();
            table.setParent(schema);
            table.setName(UObject.toString(map.get("TABLE_NAME")));
            table.setComment(UObject.toString(map.get("TABLE_COMMENT")));
            table.setSchema(schema);
            // 加载列
            table.setColumns(loadColumns(table));
            result.add(table);
        }
        return result;
    }

    @Override
    public List<DBView> loadViews(DBSchema schema) throws Exception {
        List<DBView> result = new ArrayList<>();
        List<DBResult> list = conn.getResultSet(String.format(getViewSql(), schema.getName()));
        for (DBResult map : list) {
            DBViewImpl view = new DBViewImpl();
            view.setParent(schema);
            view.setName(UObject.toString(map.get("VIEW_NAME")));
            view.setComment(UObject.toString(map.get("VIEW_COMMENT")));
            view.setSchema(schema);
            // 加载列
//            view.setColumns(loadColumns(view));
            result.add(view);
        }
        return result;
    }

    @Override
    public List<DBColumn> loadColumns(DBTable table) throws Exception {
        List<DBColumn> result = new ArrayList<>();
        List<DBResult> list = conn.getResultSet(String.format(getColumnSql(), table.getSchema().getName(), table.getName()));
        for (DBResult map : list) {
            DBColumnImpl column = new DBColumnImpl();
            column.setParent(table);
            column.setName(UObject.toString(map.get("COLUMN_NAME")));
            column.setComment(UObject.toString(map.get("COLUMN_COMMENT")));
            column.setDataType(MetaDataType.getDataType(map.getString("DATA_TYPE_NAME")));

            result.add(column);
        }
        return result;
    }
}
