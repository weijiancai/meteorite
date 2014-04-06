package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.DBObjCache;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.util.UString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBObjectImpl implements DBObject {
    private String name;
    private String comment;
    private DBSchema schema;
    private DBObjectType objectType;
    private DBObject parent;
    private List<DBObject> children;

    private String icon;

    public DBObjectImpl() {}

    public DBObjectImpl(String name, String comment, List<DBObject> children) {
        setName(name);
        this.comment = comment;
        this.children = children;
    }

    @Override @XmlAttribute
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        if (getParent() != null) {
            return getParent().getFullName() + "." + name;
        }
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
        // 放入缓存
        DBObjCache.getInstance().addDbObject(getFullName(), this);
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

    public DBObject getParent() {
        return parent;
    }

    public void setParent(DBObject parent) {
        this.parent = parent;
    }

    public void setChildren(List<DBObject> children) {
        this.children = children;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
