package com.meteorite.core.ui.layout.property;

import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.ui.model.LayoutProperty;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseProperty {
    protected  Layout layout;
    private Map<String, LayoutProperty> map = new HashMap<String, LayoutProperty>();

    public BaseProperty(Layout layout) {
        this.layout = layout;

        for (LayoutProperty property : layout.getProperties()) {
            map.put(property.getName(), property);
        }
    }

    /*protected String getPropStringValue(String propertyName) {
        System.out.println(layout.getName() + " Get Property : " + propertyName);
        return map.get(propertyName).getValue();
    }

    protected void setPropertyValue(String propertyName, String propertyValue) {
        map.get(propertyName).setValue(propertyValue);
    }*/
}
