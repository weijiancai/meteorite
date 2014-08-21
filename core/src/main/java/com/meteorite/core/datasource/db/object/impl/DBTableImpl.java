package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBIndex;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.datasource.db.object.DBTrigger;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Table")
public class DBTableImpl extends DBDatasetImpl implements DBTable {
    private List<DBIndex> indexes;
    private List<DBTrigger> triggers;

    public DBTableImpl() {
        setObjectType(DBObjectType.TABLE);
    }

    @Override
    public List<DBIndex> getIndexes() {
        if (indexes == null) {
            indexes = new ArrayList<DBIndex>();
        }
        return indexes;
    }

    @Override
    public List<DBTrigger> getTriggers() {
        if (triggers == null) {
            triggers = new ArrayList<DBTrigger>();
        }
        return triggers;
    }

    public void setIndexes(List<DBIndex> indexes) {
        this.indexes = indexes;
    }

    public void setTriggers(List<DBTrigger> triggers) {
        this.triggers = triggers;
    }

    @Override
    public String getFullName() {
        if (getSchema() == null) {
            return getName();
        }
        return getSchema().getFullName() + "." + getName();
    }
}
