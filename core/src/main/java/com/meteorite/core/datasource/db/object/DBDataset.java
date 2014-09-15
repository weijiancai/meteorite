package com.meteorite.core.datasource.db.object;

import java.util.List;

/**
 * 数据集
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBDataset extends DBObject {
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
    List<DBColumn> getColumns() throws Exception;

    /**
     * 获得数据库表的主键列
     *
     * @return 返回主键列
     */
    List<DBColumn> getPkColumns() throws Exception;

    /**
     * 获得数据库表的约束信息
     *
     * @return 返回数据库表中的约束信息
     * @since 1.0.0
     */
    List<DBConstraint> getConstraints() throws Exception;

    /**
     * 获得数据库表的外键约束信息
     *
     * @return 返回数据库表的外键约束信息
     * @throws Exception
     */
    List<DBConstraint> getFkConstraints() throws Exception;

    /**
     * 根据约束名，获得约束信息
     *
     * @param name 约束名
     * @return 返回约束信息
     * @since 1.0.0
     */
    DBConstraint getConstraint(String name);
}
