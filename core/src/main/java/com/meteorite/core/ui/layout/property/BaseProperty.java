package com.meteorite.core.ui.layout.property;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.ViewProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性基类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseProperty implements PropertyNames {
    private Map<String, ViewProperty> propMap;
    private Map<String, ViewProperty> propNameMap;
    private MetaField metaField;
    private DBColumn dbColumn;

    public BaseProperty(MetaField field, Map<String, ViewProperty> propMap) {
        this.propMap = propMap;
        this.metaField = field;
        this.dbColumn = field.getColumn();

        propNameMap = new HashMap<>();
        for (ViewProperty prop : propMap.values()) {
            propNameMap.put(prop.getProperty().getName(), prop);
        }
    }

    public ViewProperty getProperty(String propId) {
        return propMap.get(propId);
    }

    public String getPropertyValue(String prop) {
        return propMap.get(prop).getValue();
    }

    public DBColumn getDbColumn() {
        return dbColumn;
    }

    public MetaField getMetaField() {
        return metaField;
    }

    public ViewProperty getPropertyByName(String propName) {
        return propNameMap.get(propName);
    }
}
