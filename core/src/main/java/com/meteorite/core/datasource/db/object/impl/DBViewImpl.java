package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.DBView;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBViewImpl extends DBDatasetImpl implements DBView {
    public DBViewImpl() {
        setObjectType(DBObjectType.VIEW);
    }

    @Override
    public String getFullName() {
        return getSchema().getFullName() + "." + getName();
    }
}
