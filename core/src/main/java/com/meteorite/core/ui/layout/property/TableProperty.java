package com.meteorite.core.ui.layout.property;

import com.meteorite.core.dict.EnumAlign;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.*;

/**
 * 表格属性配置
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableProperty implements PropertyNames {
    private List<TableFieldProperty> fieldProperties;
    private Map<MetaField, TableFieldProperty> fieldMap = new HashMap<>();

    public TableProperty(View view) {
        fieldProperties = new ArrayList<>();
        for (MetaField field : view.getMetaFieldList()) {
            Map<String, ViewProperty> map = view.getMetaFieldConfig(field);
            TableFieldProperty fieldProperty = new TableFieldProperty(field, map);
            fieldProperties.add(fieldProperty);
            fieldMap.put(field, fieldProperty);
        }
        // 排序
        Collections.sort(fieldProperties, new Comparator<TableFieldProperty>() {
            @Override
            public int compare(TableFieldProperty o1, TableFieldProperty o2) {
                return o1.getSortNum() - o2.getSortNum();
            }
        });
    }

    public List<TableFieldProperty> getFieldProperties() {
        return fieldProperties;
    }

    public void setPropertyValue(MetaField field, String propName, String propValue) {
        TableFieldProperty fieldProperty = fieldMap.get(field);
        if (fieldProperty != null) {
            if ("width".equalsIgnoreCase(propName)) {
                fieldProperty.setWidth(UNumber.toInt(propValue));
            } else if ("isDisplay".equalsIgnoreCase(propName)) {
                fieldProperty.setDisplay(UString.toBoolean(propValue));
            } else if ("displayName".equalsIgnoreCase(propName)) {
                fieldProperty.setDisplayName(propValue);
            } else if ("align".equalsIgnoreCase(propName)) {
                fieldProperty.setAlign(EnumAlign.getAlign(propValue));
            }
        }
    }

    public static View createTableView(Meta meta) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "TableView");
        view.setDisplayName(meta.getDisplayName() + "表格视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewProperty> viewProperties = new ArrayList<>();


        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(TableFieldProperty.getViewProperties(view, field));
        }
        view.setViewProperties(viewProperties);

        return view;
    }
}
