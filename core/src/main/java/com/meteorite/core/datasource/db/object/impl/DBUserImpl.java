package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.DBUser;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBUserImpl extends DBObjectImpl implements DBUser {
    public DBUserImpl() {
        setObjectType(DBObjectType.USER);
    }
}
