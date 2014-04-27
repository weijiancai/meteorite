package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
public class DBTableImpl extends DBDatasetImpl implements DBTable {

    public DBTableImpl() {
        setObjectType(DBObjectType.TABLE);
    }
}
