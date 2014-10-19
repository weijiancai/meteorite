package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

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
//    private DBColumn dbColumn;

    public BaseProperty(MetaField field, Map<String, ViewProperty> propMap) {
        this.propMap = propMap;
        this.metaField = field;
//        this.dbColumn = field.getColumn();

        propNameMap = new HashMap<String, ViewProperty>();
        for (ViewProperty prop : propMap.values()) {
            propNameMap.put(prop.getProperty().getName(), prop);
        }
    }

    public BaseProperty() {
    }

    public ViewProperty getProperty(String propId) {
        return propMap.get(propId);
    }

    public String getPropertyValue(String prop) {
        ViewProperty vp = propMap.get(prop);
        if (vp == null) {
            throw new RuntimeException("不存在属性：" + prop);
        }
        return vp.getValue();
    }

    public String getPropertyValue(String prop, String defaultValue) {
        ViewProperty vp = propMap.get(prop);
        if (vp == null) {
            return defaultValue;
        }
        return vp.getValue();
    }

    public int getIntPropertyValue(String prop, int defaultValue) {
        ViewProperty vp = propMap.get(prop);
        if (vp == null) {
            return defaultValue;
        }
        return UNumber.toInt(vp.getValue());
    }

    public boolean getBooleanPropertyValue(String prop, boolean defaultValue) {
        ViewProperty vp = propMap.get(prop);
        if (vp == null) {
            return defaultValue;
        }
        return UString.toBoolean(vp.getValue());
    }

    public MetaField getMetaField() {
        return metaField;
    }

    public ViewProperty getPropertyByName(String propName) {
        return propNameMap.get(propName);
    }
}
