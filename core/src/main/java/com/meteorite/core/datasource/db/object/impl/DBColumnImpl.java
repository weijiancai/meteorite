package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 数据库列信息实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Column")
public class DBColumnImpl extends DBObjectImpl implements DBColumn {

    public DBColumnImpl() {
        setObjectType(DBObjectType.COLUMN);
    }

    @Override
    public List<DBObject> getChildren() {
        return null;
    }
}
