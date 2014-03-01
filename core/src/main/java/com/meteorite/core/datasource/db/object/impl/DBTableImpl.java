package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
@XmlType(propOrder = {"name", "comment"})
public class DBTableImpl implements DBTable {
    private String name;
    private String comment;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
