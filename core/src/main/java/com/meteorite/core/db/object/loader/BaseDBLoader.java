package com.meteorite.core.db.object.loader;

import com.meteorite.core.db.object.DBConnection;
import com.meteorite.core.db.object.DBLoader;
import com.meteorite.core.db.object.DBSchema;
import com.meteorite.core.db.object.DBTable;
import com.meteorite.core.db.object.impl.DBSchemaImpl;
import com.meteorite.core.db.object.impl.DBTableImpl;
import com.meteorite.core.util.UObject;

import java.util.ArrayList;
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

    // 获得Schema Sql语句
    protected abstract String getSchemaSql();
    // 获得Table sql语句
    protected abstract String getTableSql();

    @Override
    public List<DBSchema> loadSchemas() throws Exception {
        List<DBSchema> result = new ArrayList<DBSchema>();
        List<Map<String, Object>> list = conn.getResultSet(getSchemaSql());
        for (Map<String, Object> map : list) {
            DBSchemaImpl schema = new DBSchemaImpl();
            schema.setName(UObject.toString(map.get("SCHEMA_NAME")));
            schema.setComment(schema.getName());
            // 加载Table
            schema.setTables(loadTables(schema));

            result.add(schema);
        }
        return result;
    }

    @Override
    public List<DBTable> loadTables(DBSchema schema) throws Exception {
        List<DBTable> result = new ArrayList<DBTable>();
        List<Map<String, Object>> list = conn.getResultSet(String.format(getTableSql(), schema.getName()));
        for (Map<String, Object> map : list) {
            DBTableImpl table = new DBTableImpl();
            table.setName(UObject.toString(map.get("TABLE_NAME")));
            table.setComment(UObject.toString(map.get("TABLE_COMMENT")));
            result.add(table);
        }
        return result;
    }
}
