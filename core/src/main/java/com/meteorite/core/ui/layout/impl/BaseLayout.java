package com.meteorite.core.ui.layout.impl;

import com.meteorite.core.ui.ILayout;
import com.meteorite.core.ui.layout.LayoutProperty;
import com.meteorite.core.ui.layout.LayoutType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础布局
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseLayout implements ILayout {
    private Map<LayoutType, List<LayoutProperty>> properties;

    @Override
    public void setProperties(Map<LayoutType, List<LayoutProperty>> properties) {
        this.properties = properties;
    }

    @Override
    public void addProperty(LayoutType type, LayoutProperty property) {
        if(properties.containsKey(type)) {
            properties.get(type).add(property);
        } else {
            List<LayoutProperty> list = new ArrayList<LayoutProperty>();
            list.add(property);
            properties.put(type, list);
        }
    }

    protected LayoutProperty getLayoutProperty(LayoutType type, String propertyName) {
        for (LayoutProperty property : properties.get(type)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }

    protected void setPropertyValue(LayoutType type, String propertyName, String value) {
        getLayoutProperty(type, propertyName).setValue(value);
    }

    protected String getPropertyValue(LayoutType type, String propertyName) {
        return getLayoutProperty(type, propertyName).getValue();
    }
}
