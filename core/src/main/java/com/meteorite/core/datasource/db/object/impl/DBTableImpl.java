package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
public class DBTableImpl extends DBObjectImpl implements DBTable {
    private List<DBColumn> columns;

    public DBTableImpl() {
        setObjectType(DBObjectType.TABLE);
    }

    @Override
    public DBObjectType getObjectType() {
        return DBObjectType.TABLE;
    }

    @Override
    public List<DBObject> getChildren() {
        return new ArrayList<DBObject>(columns);
    }

    @Override
    @XmlElementWrapper(name = "Columns")
    @XmlAnyElement
    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }
}
