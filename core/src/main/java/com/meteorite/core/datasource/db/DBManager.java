package com.meteorite.core.datasource.db;

/**
 * 数据库管理
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DBManager {
    /**
     * 获得数据库缓存对象
     *
     * @return 返回数据库缓存对象
     * @since 1.0.0
     */
    public static DBObjCache getCache() {
        return DBObjCache.getInstance();
    }
}
