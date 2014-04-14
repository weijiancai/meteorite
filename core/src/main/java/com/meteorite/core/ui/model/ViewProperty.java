package com.meteorite.core.ui.model;

import com.meteorite.core.meta.model.MetaField;

/**
 * 视图属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewProperty {
    /** 视图属性ID */
    private String id;
    /** 属性值*/
    private String value;

    private View view;
    private MetaField field;
    private LayoutProperty property;

    public ViewProperty() {
    }

    public ViewProperty(View view, LayoutProperty property, String value) {
        this.value = value;
        this.view = view;
        this.property = property;
    }

    public ViewProperty(View view, LayoutProperty property, MetaField field, String value) {
        this.value = value;
        this.view = view;
        this.field = field;
        this.property = property;
    }

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

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
