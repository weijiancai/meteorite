package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.model.ViewLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TableProperty {
    private List<TableFieldProperty> fieldProperties;

    public TableProperty(ViewLayout viewLayout) {
        fieldProperties = new ArrayList<>();
        for (MetaField field : viewLayout.getMeta().getFields()) {
            Map<String, String> map = viewLayout.getMetaFieldConfig(field.getId());
            fieldProperties.add(new TableFieldProperty(field, map));
        }
    }

    public List<TableFieldProperty> getFieldProperties() {
        return fieldProperties;
    }
}
