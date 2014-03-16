package com.meteorite.core.datasource.db.object;

import com.meteorite.core.datasource.db.DBIcons;

/**
 * 数据库对象类型
 * 
 * @author wei_jc
 * @since 1.0.0
 */
public enum DBObjectType {
    ARGUMENT(null, null),
    CATEGORY(null, null),
    CHARSET(null, null),
    CLUSTER(null, null),
    COLUMN(DBIcons.DBO_COLUMN, DBIcons.DBO_COLUMNS),
    CONSTRAINT(null, null),
    DATABASE(null, null),
    DATASET(null, null),
    DBLINK(null, null),
    DIMENSION(null, null),
    DIMENSION_ATTRIBUTE(null, null),
    DIMENSION_HIERARCHY(null, null),
    DIMENSION_LEVEL(null, null),
    DISKGROUP(null, null),
    DOMAIN(null, null),
    FUNCTION(null, null),
    GRANTED_PRIVILEGE(null, null),
    GRANTED_ROLE(null, null),
    INDEX(null, null),
    INDEXTYPE(null, null),
    JAVA_OBJECT(null, null),
    LOB(null, null),
    MATERIALIZED_VIEW(null, null),
    METHOD(null, null),
    MODEL(null, null),
    NESTED_TABLE(null, null),
    NESTED_TABLE_COLUMN(null, null),
    OPERATOR(null, null),
    OUTLINE(null, null),
    PACKAGE(null, null),
    PACKAGE_FUNCTION(null, null),
    PACKAGE_PROCEDURE(null, null),
    PACKAGE_TYPE(null, null),
    PACKAGE_BODY(null, null),
    PARTITION(null, null),
    PRIVILEGE(null, null),
    PROCEDURE(null, null),
    PROFILE(null, null),
    ROLLBACK_SEGMENT(null, null),
    ROLE(null, null),
    SCHEMA(DBIcons.DBO_SCHEMA, DBIcons.DBO_SCHEMAS),
    SEQUENCE(null, null),
    SUBPARTITION(null, null),
    SYNONYM(null, null),
    TABLE(DBIcons.DBO_TABLE, DBIcons.DBO_TABLES),
    TABLESPACE(null, null),
    TRIGGER(null, null),
    TYPE(null, null),
    TYPE_ATTRIBUTE(null, null),
    TYPE_FUNCTION(null, null),
    TYPE_PROCEDURE(null, null),
    USER(null, null),
    VARRAY(null, null),
    VARRAY_TYPE(null, null),
    VIEW(DBIcons.DBO_VIEW, DBIcons.DBO_VIEWS),


    CURSOR(null, null),
    PARAMETER(null, null),
    LABEL(null, null),
    SAVEPOINT(null, null),
    EXCEPTION(null, null),


    UNKNOWN(null, null),
    NONE(null, null),
    ANY(null, null);

    private String icon;
    private String listIcon;

    private DBObjectType(String icon, String listIcon) {
        this.icon = icon;
        this.listIcon = listIcon;
    }

    public String getIcon() {
        return icon;
    }

    public String getListIcon() {
        return listIcon;
    }
}
