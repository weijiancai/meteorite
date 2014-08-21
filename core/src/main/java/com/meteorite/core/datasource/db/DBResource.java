package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.object.DBObject;

/**
 * 数据库资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBResource extends VirtualResource {
    private DBObject dbObject;

    public DBResource(DBObject dbObject) {
        this.dbObject = dbObject;
    }

    @Override
    public String getName() {
        return dbObject.getName();
    }

    @Override
    public String getPath() {
        return dbObject.getFullName();
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public VirtualResource getParent() {
        return new DBResource((DBObject) dbObject.getParent());
    }

    @Override
    public VirtualResource[] getChildren() {
        return new VirtualResource[0];
    }

    @Override
    public void delete(Object requestor) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
