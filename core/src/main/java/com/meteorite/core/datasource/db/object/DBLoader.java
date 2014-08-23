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

    DBColumn getColumn(String tableName, String columnName) throws Exception;

    void deleteIndex(String tableName, String indexName) throws Exception;

    /**
     * 删除表，如果此表有外键约束，则删除此表关联的外键约束
     * @param tableName 表名
     * @throws Exception
     */
    void dropTable(String tableName) throws Exception;

    /**
     * 删除外键约束
     *
     * @param tableName 表名
     * @param referenceName 外键约束名
     * @throws Exception
     */
    void dropForeignKey(String tableName, String referenceName) throws Exception;

    List<DBConstraint> getExportedKeys(String table) throws Exception;

    void updateColumnNullable(String table, String column, boolean nullable) throws Exception;
}
