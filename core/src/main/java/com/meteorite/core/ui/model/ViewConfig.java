package com.meteorite.core.ui.model;

import com.meteorite.core.meta.model.MetaField;

/**
 * 视图配置
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewConfig {
    /** 视图配置ID */
    private String id;
    /** 属性值*/
    private String value;

    private ViewLayout viewLayout;
    private MetaField field;
    private LayoutProperty property;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ViewLayout getViewLayout() {
        return viewLayout;
    }

    public void setViewLayout(ViewLayout viewLayout) {
        this.viewLayout = viewLayout;
    }

    public MetaField getField() {
        return field;
    }

    public void setField(MetaField field) {
        this.field = field;
    }

    public LayoutProperty getProperty() {
        return property;
    }

    public void setProperty(LayoutProperty property) {
        this.property = property;
    }
}
