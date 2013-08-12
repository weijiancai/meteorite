package com.meteorite.core.db.object.loader;

import com.meteorite.core.db.object.DBConnection;
import com.meteorite.core.db.object.DBLoader;
import com.meteorite.core.db.object.DBSchema;
import com.meteorite.core.db.object.DBTable;

import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 0.0.1
 */
public class MySqlLoader implements DBLoader {
    public static final String SCHEMAS = "select\n" +
            "                SCHEMA_NAME,\n" +
            "                'N' as IS_PUBLIC,\n" +
            "                if(lower(SCHEMA_NAME)='information_schema', 'Y', 'N') as IS_SYSTEM\n" +
            "            from INFORMATION_SCHEMA.SCHEMATA\n" +
            "            order by SCHEMA_NAME asc";

    private DBConnection conn;

    public MySqlLoader(DBConnection conn) {
        this.conn = conn;
    }

    @Override
    public List<DBSchema> loadSchemas() throws Exception {
        List<Map<String, Object>> list = conn.getResultSet(SCHEMAS);

        return null;
    }

    @Override
    public List<DBTable> loadTables() {
        return null;
    }
}
