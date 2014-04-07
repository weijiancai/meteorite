package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.model.LayoutProperty;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.util.UUIDUtil;

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

    public static ViewLayout createViewLayout(Meta meta) {
        ViewLayout viewLayout = new ViewLayout();
        viewLayout.setId(UUIDUtil.getUUID());
        viewLayout.setMeta(meta);
        viewLayout.setLayout(LayoutManager.getLayoutByName("TABLE"));

        List<ViewConfig> configList = new ArrayList<>();

        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            configList.addAll(TableFieldProperty.getViewConfigs(viewLayout, field));
        }
        viewLayout.setConfigs(configList);

        return viewLayout;
    }
}
