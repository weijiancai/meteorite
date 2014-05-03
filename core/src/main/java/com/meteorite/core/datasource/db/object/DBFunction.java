package com.meteorite.core.datasource.db.object;

/**
 * 数据库函数
 *
 * @author wei_jc
 * @since  1.0.0
 */
public interface DBFunction extends DBMethod {
    /**
     * 获得函数的返回参数信息
     *
     * @return 返回函数的返回参数信息
     * @since 1.0.0
     */
    DBArgument getReturnArgument();
}
