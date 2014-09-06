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
    private DBTable primaryKeyTable;
    private DBTable foreignKeyTable;
    private List<DBColumn> columns;

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
    public DBTable getPrimaryKeyTable() {
        return primaryKeyTable;
    }

    @Override
    public DBTable getForeignKeyTable() {
        return foreignKeyTable;
    }

    @Override
    public List<DBColumn> getColumns() {
        if (columns == null) {
            columns = new ArrayList<DBColumn>();
        }
        return columns;
    }

    public void setConstraintType(DBConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    public void setPrimaryKeyTable(DBTable primaryKeyTable) {
        this.primaryKeyTable = primaryKeyTable;
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

    @Override
    public String getFullName() {
        if (getSchema() == null) {
            return getName();
        }
        return getSchema().getFullName() + ".constraint." + getName();
    }



    private String pkCatalog;
    private String pkSchema;
    private String pkTableName;
    private String pkColumnName;
    private String fkCatalog;
    private String fkSchema;
    private String fkTableName;
    private String fkColumnName;
    private int keySeq;
    private String updateRule;
    private String deleteRule;
    private String fkName;
    private String pkName;

    @Override
    public String getPkCatalog() {
        return pkCatalog;
    }

    @Override
    public String getPkSchema() {
        return pkSchema;
    }

    @Override
    public String getPkTableName() {
        return pkTableName;
    }

    @Override
    public String getPkColumnName() {
        return pkColumnName;
    }

    @Override
    public String getFkCatalog() {
        return fkCatalog;
    }

    @Override
    public String getFkSchema() {
        return fkSchema;
    }

    @Override
    public String getFkTableName() {
        return fkTableName;
    }

    @Override
    public String getFkColumnName() {
        return fkColumnName;
    }

    @Override
    public int getKeySeq() {
        return keySeq;
    }

    @Override
    public String getUpdateRule() {
        return updateRule;
    }

    @Override
    public String getDeleteRule() {
        return deleteRule;
    }

    @Override
    public String getFkName() {
        return fkName;
    }

    @Override
    public String getPkName() {
        return pkName;
    }

    public void setPkCatalog(String pkCatalog) {
        this.pkCatalog = pkCatalog;
    }

    public void setPkSchema(String pkSchema) {
        this.pkSchema = pkSchema;
    }

    public void setPkTableName(String pkTableName) {
        this.pkTableName = pkTableName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    public void setFkCatalog(String fkCatalog) {
        this.fkCatalog = fkCatalog;
    }

    public void setFkSchema(String fkSchema) {
        this.fkSchema = fkSchema;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public void setKeySeq(int keySeq) {
        this.keySeq = keySeq;
    }

    public void setUpdateRule(String updateRule) {
        this.updateRule = updateRule;
    }

    public void setDeleteRule(String deleteRule) {
        this.deleteRule = deleteRule;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }
}
