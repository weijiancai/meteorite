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
     * 获得数据类型
     *
     * @return 返回数据类型
     * @since 1.0.0
     */
    MetaDataType getDataType();

    boolean isPk();

    boolean isFk();

    /**
     * 获得最大长度
     *
     * @return 返回最大长度
     */
    int getMaxLength();
}
