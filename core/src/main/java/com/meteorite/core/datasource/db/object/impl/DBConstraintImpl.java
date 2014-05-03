package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBConstraintType;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBConstraintImpl extends DBObjectImpl implements DBConstraint {
    private DBConstraintType constraintType;
    private boolean isPrimaryKey;
    private boolean isForeignKey;
    private boolean isUniqueKey;
    private DBConstraint foreignKeyConstraint;
    private DBTable foreignKeyTable;
    List<DBColumn> columns;

    public DBConstraintImpl() {
        setObjectType(DBObjectType.CONSTRAINT);
    }

    @Override
    public DBConstraintType getConstraintType() {
        return constraintType;
    }

    @Override
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    @Override
    public boolean isForeignKey() {
        return isForeignKey;
    }

    @Override
    public boolean isUniqueKey() {
        return isUniqueKey;
    }

    @Override
    public DBConstraint getForeignKeyConstraint() {
        return foreignKeyConstraint;
    }

    @Override
    public DBTable getForeignKeyTable() {
        return foreignKeyTable;
    }

    @Override
    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setConstraintType(DBConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public void setForeignKey(boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public void setUniqueKey(boolean isUniqueKey) {
        this.isUniqueKey = isUniqueKey;
    }

    public void setForeignKeyConstraint(DBConstraint foreignKeyConstraint) {
        this.foreignKeyConstraint = foreignKeyConstraint;
    }

    public void setForeignKeyTable(DBTable foreignKeyTable) {
        this.foreignKeyTable = foreignKeyTable;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }
}
