package com.meteorite.core.db.object.impl;

import com.meteorite.core.db.object.*;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author weijiancai
 * @version 1.0.0
 */
@XmlRootElement(name = "Schema")
public class DBSchemaImpl implements DBSchema {
    private String name;
    private String comment;

    private List<DBTable> tables;

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
    }

    @Override
    public List<DBView> getViews() {
        return null;
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
        return null;
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

    @Override @XmlAttribute
    public String getName() {
        return name;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
