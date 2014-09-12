package com.meteorite.core.datasource.db.object;

import java.util.List;

/**
 * 数据库索引
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface DBIndex extends DBObject {
    /**
     * 是否唯一索引
     *
     * @return 如果是唯一索引返回true，否则false
     * @since 1.0.0
     */
    boolean isUnique();

    /**
     * 是否升序
     *
     * @return 如果是升序返回true，降序返回false
     * @since 1.0.0
     */
    boolean isAsc();

    /**
     * 获得表信息
     *
     * @return 返回表信息
     * @since 1.0.0
     */
    DBTable getTable();

    /**
     * 获得索引的列信息
     *
     * @return 返回列列表
     * @since 1.0.0
     */
    List<DBColumn> getColumns();

    /**
     * 获得表名
     *
     * @return 返回表名
     */
    String getTableName();

    /**
     * 获得索引列名
     *
     * @return 返回索引列名
     */
    List<String> getColumnNames();
}
