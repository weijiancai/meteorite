package com.meteorite.core.datasource.db.object;

import java.util.List;

/**
 * 数据库表
 *
 * @author wei_jc
 * @since  1.0.0
 */
public interface DBTable extends DBObject {
    /**
     * 获得数据库表中的所有列信息
     *
     * @return 返回数据库表中的所有列信息
     */
    List<DBColumn> getColumns();
}
