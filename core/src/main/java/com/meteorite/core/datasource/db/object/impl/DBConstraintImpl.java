package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBConstraintType;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBConstraintImpl extends DBObjectImpl implements DBConstraint {
    private DBConstraintType constraintType;
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
        return constraintType == DBConstraintType.PRIMARY_KEY;
    }

    @Override
    public boolean isForeignKey() {
        return constraintType == DBConstraintType.FOREIGN_KEY;
    }

    @Override
    public boolean isUniqueKey() {
        return constraintType == DBConstraintType.UNIQUE_KEY;
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
        if (columns == null) {
            columns = new ArrayList<>();
        }
        return columns;
    }

    public void setConstraintType(DBConstraintType constraintType) {
        this.constraintType = constraintType;
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
