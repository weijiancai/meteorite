package com.meteorite.core.datasource.db.object;

import com.meteorite.core.meta.MetaDataType;

/**
 * 数据库列
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBColumn extends DBObject {
    /**
     * 获得Meta数据类型(由数据库数据类型转化)
     *
     * @return 返回Meta数据类型
     * @since 1.0.0
     */
    MetaDataType getDataType();

    /**
     * 获得数据库数据类型
     *
     * @return 返回数据库数据类型
     * @since 1.0.0
     */
    String getDbDataType();

    /**
     * 获得PRECISION
     *
     * @return 返回PRECISION
     * @since 1.0.0
     */
    int getPrecision();

    /**
     * 获得Scale
     *
     * @return 返回Scale
     * @since 1.0.0
     */
    int getScale();

    boolean isPk();

    boolean isFk();

    /**
     * 获得最大长度
     *
     * @return 返回最大长度
     */
    int getMaxLength();

    /**
     * 获得引用的主键列
     *
     * @return 返回引用的主键列
     */
    DBColumn getRefColumn();
}
