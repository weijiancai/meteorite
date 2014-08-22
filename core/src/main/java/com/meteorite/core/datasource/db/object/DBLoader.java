package com.meteorite.core.datasource.db.object;

import java.util.List;

/**
 * 数据库加载器
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBLoader {
    void load() throws Exception;

    List<DBUser> loadUsers();

    List<DBObject> loadPrivileges();

    List<DBObject> loadCharsets();

    List<DBSchema> loadSchemas();

    List<DBIndex> loadIndexes(DBSchema schema);

    List<DBTrigger> loadTriggers(DBSchema schema);

    List<DBProcedure> loadProcedures(DBSchema schema);

    List<DBFunction> loadFunctions(DBSchema schema);

    List<DBTable> loadTables(DBSchema schema);

    List<DBView> loadViews(DBSchema schema);

    List<DBColumn> loadColumns(DBDataset table);

    DBTable getTable(String tableName) throws Exception;

    void deleteIndex(String tableName, String indexName) throws Exception;
}
