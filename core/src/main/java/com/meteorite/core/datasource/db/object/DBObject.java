package com.meteorite.core.datasource.db.object;


import java.util.List;

/**
 * 数据库对象
 *
 * @author weijiancai
 * @version 1.0.0
 */
public interface DBObject {
    /**
     * 获得数据库对象名称
     *
     * @return 返回数据库对象名称
     * @since 1.0.0
     */
    String getName();

    /**
     * 获得数据库对象的全名：如schema.table.column
     *
     * @return 返回数据库对象的全名
     * @since 1.0.0
     */
    String getFullName();

    String getComment();

    /**
     * 获得数据库名称，
     *
     * @return
     */
//    String getDatabase();

    /**
     * 获得数据库对象类型
     *
     * @return 返回数据库对象类型
     */
    DBObjectType getObjectType();

    /**
     * 获得数据库Schema
     *
     * @return 返回数据库Schema
     */
    DBSchema getSchema();

    /**
     * 获得数据库对象的父对象
     *
     * @return 返回数据库对象的父对象
     * @since 1.0.0
     */
    DBObject getParent();

    /**
     * 获得数据库子对象列表
     *
     * @return 返回数据库子对象列表
     */
    List<DBObject> getChildren();

    /**
     * 获得数据库对象图标路径
     *
     * @return 返回数据库对象图标路径
     */
    String getIcon();
}
