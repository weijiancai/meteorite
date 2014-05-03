package com.meteorite.core.datasource.db.object;

/**
 * 数据库方法参数
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBArgument extends DBObject {
    /**
     * 获得数据类型
     *
     * @return 返回数据类型
     * @since 1.0.0
     */
    DBDataType getDataType();

    /**
     * 获得数据库方法
     *
     * @return 返回数据库方法
     * @since 1.0.0
     */
    DBMethod getMethod();

    /**
     * 获得参数位置
     *
     * @return 返回参数位置
     * @since 1.0.0
     */
    int getPosition();

    /**
     * 获得参数序号
     *
     * @return 返回参数序号
     * @since 1.0.0
     */
    int getSequence();

    /**
     * 是否输入参数
     *
     * @return 如果是输入参数，返回true，否则false
     * @since 1.0.0
     */
    boolean isInput();

    /**
     * 是否输出参数
     *
     * @return 如果是输出参数，返回true，否则false
     * @since 1.0.0
     */
    boolean isOutput();
}
