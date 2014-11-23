package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库对象缓存
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBObjCache {
    private static DBObjCache cache;
    private Map<String, DBObject> dbObjectMap = new HashMap<String, DBObject>();
    private static Map<String, DbmsObject> dbmsObjectMap = new HashMap<String, DbmsObject>();

    private DBObjCache() {

    }

    /**
     * 获得数据库对象缓存对象
     *
     * @return 数据库对象缓存对象
     * @since 1.0.0
     */
    public static DBObjCache getInstance() {
        if (cache == null) {
            cache = new DBObjCache();
        }

        return cache;
    }

    /**
     * 添加数据库对象到缓存
     *
     * @param key 数据库对象全名
     * @param object 数据库对象
     * @since 1.0.0
     */
    public void addDbObject(String key, DBObject object) {
        System.out.println("addCache --> " + key);
        dbObjectMap.put(key, object);
    }

    /**
     * 根据key，从缓存中获得数据库对象
     *
     * @param key 数据库对象全名
     * @return 返回数据库对象
     * @since 1.0.0
     */
    public DBObject getDbObject(String key) {
        return dbObjectMap.get(key);
    }

    /**
     * 根据key，从缓存中获得数据库列对象
     *
     * @param key 数据库对象全名
     * @return 返回数据库对象
     * @since 1.0.0
     */
    public DBColumn getColumn(String key) {
        return (DBColumn) dbObjectMap.get(key);
    }

    public Collection<DBObject> getAllDBObject() {
        return dbObjectMap.values();
    }

    public static void addCache(DBSchema schema) throws Exception {
        persist(DbmsObject.from(schema));
    }

    private static void persist(DbmsObject obj) throws Exception {
        if (dbmsObjectMap.containsKey(obj.getId())) {
            return;
        }
        JdbcTemplate.save(DataSourceManager.getSysDataSource(), MetaPDBFactory.getDbmsObject(obj));
        dbmsObjectMap.put(obj.getId(), obj);
    }
}
