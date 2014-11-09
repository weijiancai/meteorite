package com.meteorite.core.datasource.db.object.enums;

import com.meteorite.core.datasource.db.DBIcons;

/**
 * 数据库对象类型
 * 
 * @author wei_jc
 * @since 1.0.0
 */
public enum DBObjectType {
    ARGUMENT(DBIcons.DBO_ARGUMENT, DBIcons.DBO_ARGUMENTS),
    CATEGORY(null, null),
    CHARSET(null, null),
    CLUSTER(null, null),
    COLUMN(DBIcons.DBO_COLUMN, DBIcons.DBO_COLUMNS),
    CONSTRAINT(DBIcons.DBO_CONSTRAINT, DBIcons.DBO_CONSTRAINT),
    DATABASE(DBIcons.DBO_DATABASE, null),
    DATASET(null, null),
    DBLINK(null, null),
    DIMENSION(null, null),
    DIMENSION_ATTRIBUTE(null, null),
    DIMENSION_HIERARCHY(null, null),
    DIMENSION_LEVEL(null, null),
    DISKGROUP(null, null),
    DOMAIN(null, null),
    FUNCTION(DBIcons.DBO_FUNCTION, DBIcons.DBO_FUNCTIONS),
    GRANTED_PRIVILEGE(null, null),
    GRANTED_ROLE(null, null),
    INDEX(DBIcons.DBO_INDEX, DBIcons.DBO_INDEXES),
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
    PRIVILEGE(DBIcons.DBO_PRIVILEGE, DBIcons.DBO_PRIVILEGES),
    PROCEDURE(DBIcons.DBO_PROCEDURE, DBIcons.DBO_PROCEDURES),
    PROFILE(null, null),
    ROLLBACK_SEGMENT(null, null),
    ROLE(null, null),
    SCHEMA(DBIcons.DBO_SCHEMA, DBIcons.DBO_SCHEMAS),
    SEQUENCE(null, null),
    SUBPARTITION(null, null),
    SYNONYM(null, null),
    TABLE(DBIcons.DBO_TABLE, DBIcons.DBO_TABLES),
    TABLESPACE(null, null),
    TRIGGER(DBIcons.DBO_TRIGGER, DBIcons.DBO_TRIGGERS),
    TYPE(null, null),
    TYPE_ATTRIBUTE(null, null),
    TYPE_FUNCTION(null, null),
    TYPE_PROCEDURE(null, null),
    USER(DBIcons.DBO_USER, DBIcons.DBO_USERS),
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
    ANY(null, null),

    // 列表对象
    SCHEMAS(null, null),
    USERS(null, null),
    PRIVILEGES(null, null),
    CHARSETS(null, null),
    ARGUMENTS(null, null),
    TABLES(null, null),
    VIEWS(null, null),
    INDEXES(null, null),
    TRIGGERS(null, null),
    PROCEDURES(null, null),
    FUNCTIONS(null, null),
    COLUMNS(null, null),
    CONSTRAINTS(null, null)
    ;

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
