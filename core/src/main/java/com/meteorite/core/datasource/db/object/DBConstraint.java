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

    DBTable getForeignKeyTable();

    List<DBColumn> getColumns();
}