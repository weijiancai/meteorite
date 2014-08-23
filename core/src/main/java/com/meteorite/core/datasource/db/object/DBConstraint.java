package com.meteorite.core.datasource.db.object;

import com.meteorite.core.datasource.db.object.enums.DBConstraintType;

import java.util.List;

/**
 * 约束
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DBConstraint extends DBObject {
    DBConstraintType getConstraintType();
    boolean isPrimaryKey();
    boolean isForeignKey();
    boolean isUniqueKey();

    DBConstraint getForeignKeyConstraint();

    DBTable getPrimaryKeyTable();

    DBTable getForeignKeyTable();

    List<DBColumn> getColumns();

    String getPkCatalog();

    String getPkSchema();

    String getPkTableName();

    String getPkColumnName();

    String getFkCatalog();

    String getFkSchema();

    String getFkTableName();

    String getFkColumnName();

    int getKeySeq();

    String getUpdateRule();

    String getDeleteRule();

    String getFkName();

    String getPkName();
}