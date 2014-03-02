package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBTable;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
@XmlType(propOrder = {"name", "comment", "columns"})
public class DBTableImpl implements DBTable {
    private String name;
    private String comment;
    private DBSchema schema;
    private List<DBColumn> columns;

    @Override @XmlAttribute
    public String getName() {
        return name;
    }

    @Override @XmlAttribute
    public String getComment() {
        return comment;
    }

    @Override
    public DBObjectType getObjectType() {
        return DBObjectType.TABLE;
    }

    @Override @XmlTransient
    public DBSchema getSchema() {
        return schema;
    }

    @Override
    @XmlElementWrapper(name = "Columns")
    @XmlAnyElement
    @JSONField(name = "children")
    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }

    public void setSchema(DBSchema schema) {
        this.schema = schema;
    }
}
