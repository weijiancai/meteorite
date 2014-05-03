package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.datasource.db.object.DBTrigger;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.enums.DBTriggerEvent;
import com.meteorite.core.datasource.db.object.enums.DBTriggerType;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBTriggerImpl extends DBObjectImpl implements DBTrigger {
    private DBTable table;
    private boolean isForEachRow;
    private DBTriggerType triggerType;
    private List<DBTriggerEvent> triggerEvents;

    public DBTriggerImpl() {
        setObjectType(DBObjectType.TRIGGER);
    }

    @Override
    public DBTable getTable() {
        return table;
    }

    @Override
    public boolean isForEachRow() {
        return isForEachRow;
    }

    @Override
    public DBTriggerType getTriggerType() {
        return triggerType;
    }

    @Override
    public List<DBTriggerEvent> getTriggerEvents() {
        return triggerEvents;
    }

    public void setTable(DBTable table) {
        this.table = table;
    }

    public void setForEachRow(boolean isForEachRow) {
        this.isForEachRow = isForEachRow;
    }

    public void setTriggerType(DBTriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public void setTriggerEvents(List<DBTriggerEvent> triggerEvents) {
        this.triggerEvents = triggerEvents;
    }
}
