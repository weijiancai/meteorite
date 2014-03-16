package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.*;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weijiancai
 * @version 1.0.0
 */
@XmlRootElement(name = "Schema")
public class DBSchemaImpl extends DBObjectImpl implements DBSchema {
    private List<DBTable> tables;
    private List<DBView> views;
    private Map<String, DBTable> tableMap = new HashMap<String, DBTable>();

    public DBSchemaImpl() {
        setObjectType(DBObjectType.SCHEMA);
    }

    @Override
    public String getCatalog() {
        return null;
    }

    @Override
    @XmlElementWrapper(name = "Tables")
    @XmlAnyElement
    public List<DBTable> getTables() {
        return tables;
    }

    public void setTables(List<DBTable> tables) {
        this.tables = tables;
        tableMap.clear();
        for (DBTable table : tables) {
            tableMap.put(table.getName().toLowerCase(), table);
        }
    }

    @Override
    @XmlElementWrapper(name = "Views")
    @XmlAnyElement
    public List<DBView> getViews() {
        return views;
    }

    public void setViews(List<DBView> views) {
        this.views = views;
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
        return tableMap.get(name.toLowerCase());
    }

    @Override
    public DBView getView(String name) {
        return null;
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

    @Override
    @XmlTransient
    public List<DBObject> getChildren() {
        List<DBObject> list = new ArrayList<>();
        DBObjectImpl tables = new DBObjectImpl("Tables", "表", new ArrayList<DBObject>(getTables()));
        tables.setIcon(DBIcons.DBO_TABLES);
        tables.setObjectType(DBObjectType.TABLE);
        DBObjectImpl views = new DBObjectImpl("Views", "视图", new ArrayList<DBObject>(getViews()));
        views.setIcon(DBIcons.DBO_VIEWS);
        views.setObjectType(DBObjectType.VIEW);
        list.add(tables);
        list.add(views);
        return list;
    }
}
