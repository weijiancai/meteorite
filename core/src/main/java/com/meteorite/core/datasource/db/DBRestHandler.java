package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.rest.PathHandler;
import com.meteorite.core.util.UString;

import java.util.Map;

/**
 * 数据库对象Rest处理
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBRestHandler {

    public static DBObject getDBObject(String path, DBDataSource dataSource) throws Exception {
        PathHandler pathHandler = new PathHandler(path);
        Map<String, String> map = pathHandler.parseForDb();
        // 数据源
        /*String dataSourceName = map.get("datasource");
        if (UString.isEmpty(dataSourceName)) {
            return null;
        }
        DBDataSource dataSource = (DBDataSource) DataSourceManager.getDataSourceByName(dataSourceName);*/
        String schemaName = map.get("schema");
        if (UString.isEmpty(schemaName)) {
            return null;
        }
        DBSchema schema = dataSource.getSchema(schemaName);
        String tableName = map.get("table");
        if (UString.isEmpty(tableName)) {
            return schema;
        }
        DBTable table = schema.getTable(tableName);
        String columnName = map.get("column");
        if (UString.isEmpty(columnName)) {
            return table;
        }
        return table.getColumn(columnName);
    }
}
