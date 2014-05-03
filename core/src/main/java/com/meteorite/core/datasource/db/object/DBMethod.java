package com.meteorite.core.datasource.db.object;

import com.meteorite.core.datasource.db.object.enums.DBMethodType;

import java.util.List;

/**
 * 数据库方法：存储过程、函数
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBMethod extends DBObject {
    /**
     * 获得方法参数列表
     *
     * @return 返回方法参数列表
     * @since 1.0.0
     */
    List<DBArgument> getArguments();

    /**
     * 获得方法参数信息
     *
     * @param name 参数名
     * @return 返回参数信息
     * @since 1.0.0
     */
    DBArgument getArgument(String name);

    /**
     * 获得方法内容
     *
     * @return 返回方法内容
     * @since 1.0.0
     */
    String getContent();

    /**
     * 获得方法类型
     *
     * @return 返回方法类型
     * @since 1.0.0
     */
    DBMethodType getMethodType();
}
