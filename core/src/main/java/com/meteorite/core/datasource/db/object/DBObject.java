package com.meteorite.core.datasource.db.object;


/**
 * 数据库对象
 *
 * @author weijiancai
 * @version 1.0.0
 */
public interface DBObject {
    String getName();
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
}
