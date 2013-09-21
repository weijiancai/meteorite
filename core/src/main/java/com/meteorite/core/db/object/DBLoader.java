package com.meteorite.core.db.object;

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
}
