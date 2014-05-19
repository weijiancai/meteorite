package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
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
    private List<DBIndex> indexes;
    private List<DBTrigger> triggers;
    private List<DBProcedure> procedures;
    private List<DBFunction> functions;
    private List<DBConstraint> constraints;
    private Map<String, DBTable> tableMap = new HashMap<String, DBTable>();
    private Map<String, DBFunction> functionMap = new HashMap<>();
    private Map<String, DBProcedure> procedureMap = new HashMap<>();
    private Map<String, DBConstraint> constraintMap = new HashMap<>();
    private DataSource dataSource;

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

    @Override
    @XmlElementWrapper(name = "Views")
    @XmlAnyElement
    public List<DBView> getViews() {
        return views;
    }

    @Override
    public List<DBProcedure> getProcedures() {
        return procedures;
    }

    @Override
    public List<DBFunction> getFunctions() {
        return functions;
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
        return procedureMap.get(name);
    }

    @Override
    public DBFunction getFunction(String name) {
        return functionMap.get(name);
    }

    @Override
    public DBConstraint getConstraint(String name) {
        return constraintMap.get(name);
    }

    @Override
    public List<DBIndex> getIndexes() {
        return indexes;
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
        return triggers;
    }

    @Override
    public List<DBConstraint> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<>();
        }
        return constraints;
    }

    public void setTables(List<DBTable> tables) {
        this.tables = tables;
        tableMap.clear();
        for (DBTable table : tables) {
            tableMap.put(table.getName().toLowerCase(), table);
        }
    }

    public void setViews(List<DBView> views) {
        this.views = views;
    }

    public void setIndexes(List<DBIndex> indexes) {
        this.indexes = indexes;
    }

    public void setTriggers(List<DBTrigger> triggers) {
        this.triggers = triggers;
    }

    public void setProcedures(List<DBProcedure> procedures) {
        this.procedures = procedures;
        procedureMap.clear();
        for (DBProcedure procedure : procedures) {
            procedureMap.put(procedure.getName(), procedure);
        }
    }

    public void setFunctions(List<DBFunction> functions) {
        this.functions = functions;
        functionMap.clear();
        for (DBFunction function : functions) {
            functionMap.put(function.getName(), function);
        }
    }

    public void setConstraints(List<DBConstraint> constraints) {
        this.constraints = constraints;
        constraintMap.clear();
        for (DBConstraint constraint : constraints) {
            constraintMap.put(constraint.getName(), constraint);
        }
    }

    public void addConstraints(DBConstraint constraint) {
        getConstraints().add(constraint);
        constraintMap.put(constraint.getName(), constraint);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
