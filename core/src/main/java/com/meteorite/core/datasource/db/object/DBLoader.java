package com.meteorite.core.datasource.db.object;

import com.meteorite.core.datasource.db.object.loader.DBDataset;

import java.util.List;

/**
 * 数据库加载器
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBLoader {
    void load();

    List<DBUser> loadUsers() throws Exception;

    List<DBObject> loadPrivileges() throws Exception;

    List<DBObject> loadCharsets() throws Exception;

    List<DBSchema> loadSchemas() throws Exception;

    List<DBIndex> loadIndexes(DBSchema schema) throws Exception;

    List<DBTrigger> loadTriggers(DBSchema schema) throws Exception;

    List<DBProcedure> loadProcedures(DBSchema schema) throws Exception;

    List<DBFunction> loadFunctions(DBSchema schema) throws Exception;

    List<DBTable> loadTables(DBSchema schema) throws Exception;

    List<DBView> loadViews(DBSchema schema) throws Exception;

    List<DBColumn> loadColumns(DBDataset table) throws Exception;
}
