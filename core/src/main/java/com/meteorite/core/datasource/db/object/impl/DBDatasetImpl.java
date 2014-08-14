package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBConstraint;
import com.meteorite.core.datasource.db.object.DBDataset;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class DBDatasetImpl extends DBObjectImpl implements DBDataset {
    private List<DBColumn> columns;
    private List<DBConstraint> constraints;
    private Map<String, DBColumn> columnMap = new HashMap<String, DBColumn>();
    private Map<String, DBConstraint> constraintMap = new HashMap<String, DBConstraint>();

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
    public List<DBConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public DBConstraint getConstraint(String name) {
        return constraintMap.get(name);
    }

    @Override
    public List<DBColumn> getPkColumns() {
        List<DBColumn> list = new ArrayList<DBColumn>();
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

    public void setConstraints(List<DBConstraint> constraints) {
        this.constraints = constraints;
        constraintMap.clear();
        for (DBConstraint constraint : constraints) {
            constraintMap.put(constraint.getName(), constraint);
        }
    }
}
