package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.object.*;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weijiancai
 * @version 1.0.0
 */
@XmlRootElement(name = "Schema")
public class DBSchemaImpl implements DBSchema {
    private String name;
    private String comment;

    private List<DBTable> tables;
    private List<DBView> views;
    private Map<String, DBTable> tableMap = new HashMap<String, DBTable>();
    private Map<String, DBView> viewMap = new HashMap<>();

    @Override
    public String getCatalog() {
        return null;
    }

    @Override
    @XmlElementWrapper(name = "Tables")
    @XmlAnyElement
    @JSONField(name = "children")
    public List<DBTable> getTables() {
        return tables;
    }

    public void setTables(List<DBTable> tables) {
        this.tables = tables;
        tableMap.clear();
        for (DBTable table : tables) {
            tableMap.put(table.getName(), table);
        }
    }

    @Override
    public List<DBView> getViews() {
        return views;
    }

    public void setViews(List<DBView> views) {
        this.views = views;
        viewMap.clear();
        for (DBView view : views) {
            viewMap.put(view.getName(), view);
        }
    }

    @Override
    public List<DBProcedure> getProcedures() {
        return null;
    }

    @Override
    public List<DBFunction> getFunctions() {
        return null;
    }

    @Override
    public DBTable getTable(String name) {
        return tableMap.get(name);
    }

    @Override
    public DBView getView(String name) {
        return viewMap.get(name);
    }

    @Override
    public DBProcedure getProcedure(String name) {
        return null;
    }

    @Override
    public DBFunction getFunction(String name) {
        return null;
    }

    @Override
    public List<DBIndex> getIndexes() {
        return null;
    }

    @Override
    public List<DBSynonym> getSynonyms() {
        return null;
    }

    @Override
    public List<DBSequence> getSequences() {
        return null;
    }

    @Override
    public List<DBPackage> getPackages() {
        return null;
    }

    @Override
    public List<DBTrigger> getTriggers() {
        return null;
    }

    @Override @XmlAttribute
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public DBObjectType getObjectType() {
        return DBObjectType.SCHEMA;
    }

    @Override
    public DBSchema getSchema() {
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
