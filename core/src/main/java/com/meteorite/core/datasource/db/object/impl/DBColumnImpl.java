package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBSchema;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * 数据库列信息实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Column")
@XmlType(propOrder = {"name", "comment"})
public class DBColumnImpl implements DBColumn {
    private String name;
    private String comment;
    private DBSchema schema;

    @Override @XmlAttribute
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override @XmlAttribute
    public String getComment() {
        return comment;
    }

    @Override
    public DBObjectType getObjectType() {
        return DBObjectType.COLUMN;
    }

    @Override @XmlTransient
    public DBSchema getSchema() {
        return schema;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSchema(DBSchema schema) {
        this.schema = schema;
    }
}
