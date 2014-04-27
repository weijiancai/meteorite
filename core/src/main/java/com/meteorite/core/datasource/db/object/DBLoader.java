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
    List<DBSchema> loadSchemas() throws Exception;

    List<DBTable> loadTables(DBSchema schema) throws Exception;

    List<DBView> loadViews(DBSchema schema) throws Exception;

    List<DBColumn> loadColumns(DBDataset table) throws Exception;
}
