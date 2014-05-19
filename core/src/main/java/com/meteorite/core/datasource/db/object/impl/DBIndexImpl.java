package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBIndex;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;

import java.util.List;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBIndexImpl extends DBObjectImpl implements DBIndex {
    private boolean isUnique;
    private boolean isAsc;
    private DBTable table;
    private List<DBColumn> columns;

    public DBIndexImpl() {
        setObjectType(DBObjectType.INDEX);
    }

    @Override
    public boolean isUnique() {
        return isUnique;
    }

    @Override
    public boolean isAsc() {
        return isAsc;
    }

    @Override
    public DBTable getTable() {
        return table;
    }

    @Override
    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public void setTable(DBTable table) {
        this.table = table;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }

    @Override
    public String getFullName() {
        return getSchema().getFullName() + "." + getName();
    }
}
