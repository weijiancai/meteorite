package com.meteorite.core.ui.layout.property;

import com.meteorite.core.ui.ILayoutProperties;
import com.meteorite.core.ui.layout.LayoutType;
import com.meteorite.core.ui.layout.model.LayoutProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutProperties implements ILayoutProperties {
    private Map<LayoutType, List<LayoutProperty>> properties = new HashMap<LayoutType, List<LayoutProperty>>();

    @Override
    public void setProperties(Map<LayoutType, List<LayoutProperty>> properties) {
        this.properties = properties;
    }

    public void addProperty(LayoutType type, LayoutProperty property) {
        if(properties.containsKey(type)) {
            properties.get(type).add(property);
        } else {
            List<LayoutProperty> list = new ArrayList<LayoutProperty>();
            list.add(property);
            properties.put(type, list);
        }
    }

    public LayoutProperty getLayoutProperty(LayoutType type, String propertyName) {
        for (LayoutProperty property : properties.get(type)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public void setPropertyValue(LayoutType type, String propertyName, String value) {
        getLayoutProperty(type, propertyName).setValue(value);
    }

    public String getPropertyValue(LayoutType type, String propertyName) {
        return getLayoutProperty(type, propertyName).getValue();
    }
}
