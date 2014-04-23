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
     * 根据列名获得数据库列信息
     *
     * @param columnName 列名
     * @return 返回数据库列信息
     */
    DBColumn getColumn(String columnName);

    /**
     * 获得数据库表中的所有列信息
     *
     * @return 返回数据库表中的所有列信息
     */
    List<DBColumn> getColumns();

    /**
     * 获得数据库表的主键列
     *
     * @return 返回主键列
     */
    List<DBColumn> getPkColumns();
}
