package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
public class DBTableImpl extends DBObjectImpl implements DBTable {
    private List<DBColumn> columns;
    private Map<String, DBColumn> columnMap = new HashMap<>();

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
    public DBColumn getColumn(String columnName) {
        return columnMap.get(columnName.toLowerCase());
    }

    @Override
    @XmlElementWrapper(name = "Columns")
    @XmlAnyElement
    public List<DBColumn> getColumns() {
        return columns;
    }

    @Override
    public List<DBColumn> getPkColumns() {
        List<DBColumn> list = new ArrayList<>();
        for (DBColumn column : getColumns()) {
            if (column.isPk()) {
                list.add(column);
            }
        }
        return list;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
        columnMap.clear();
        for (DBColumn column : columns) {
            columnMap.put(column.getName().toLowerCase(), column);
        }
    }
}
