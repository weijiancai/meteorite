package com.meteorite.core.datasource;

import com.meteorite.core.meta.model.Meta;

/**
 * 数据源接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DataSource {
    /**
     * 获得数据源名称
     *
     * @return 返回数据源名称
     */
    String getName();

    /**
     * 获得数据源类型
     *
     * @return 返回数据源类型
     */
    DataSourceType getType();

    /**
     * 获得数据源连接属性
     *
     * @return 返回数据源连接属性
     */
    Meta getProperties();
}
