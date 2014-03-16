package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.util.UString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBObjectImpl implements DBObject {
    private String name;
    private String comment;
    private DBSchema schema;
    private DBObjectType objectType;
    private List<DBObject> children;

    private String icon;

    public DBObjectImpl() {}

    public DBObjectImpl(String name, String comment, List<DBObject> children) {
        this.name = name;
        this.comment = comment;
        this.children = children;
    }

    @Override @XmlAttribute
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override @XmlElement
    public String getComment() {
        return comment;
    }

    @Override
    public DBObjectType getObjectType() {
        return objectType;
    }

    @Override
    @XmlTransient
    public DBSchema getSchema() {
        return schema;
    }

    @Override
    @XmlTransient
    public List<DBObject> getChildren() {
        return children;
    }

    @Override
    public String getIcon() {
        return UString.isEmpty(icon) ? objectType.getIcon() : icon;
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

    public void setObjectType(DBObjectType objectType) {
        this.objectType = objectType;
    }

    public void setChildren(List<DBObject> children) {
        this.children = children;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
