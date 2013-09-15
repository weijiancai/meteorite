package com.meteorite.core.ui.config.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.ui.layout.property.FormProperty;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FormFieldLayoutConfig extends LayoutConfig {
    private MetaField metaField;

    public FormFieldLayoutConfig(MetaField field) {
        this.metaField = field;

        this.setDisplayName(field.getDisplayName());
        this.setDisplay(true);
        this.setName(field.getName());
        this.setWidth(180);
        this.setMetaField(field);
    }

    public String getDisplayName() {
        return getPropStringValue(FORM_FIELD_DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) {
        setPropValue(FORM_FIELD_DISPLAY_NAME, displayName);
    }

    public boolean isSingleLine() {
        return getPropBooleanValue(FORM_FIELD_IS_SINGLE_LINE);
    }

    public void setSingleLine(boolean singleLine) {
        setPropValue(FORM_FIELD_IS_SINGLE_LINE, singleLine + "");
    }

    public boolean isDisplay() {
        return getPropBooleanValue(FORM_FIELD_IS_DISPLAY);
    }

    public void setDisplay(boolean display) {
        setPropValue(FORM_FIELD_IS_DISPLAY, isDisplay() + "");
    }

    /*@XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }*/

    public int getWidth() {
        return getPropIntValue(FORM_FIELD_WIDTH);
    }

    public void setWidth(int width) {
        setPropValue(FORM_FIELD_WIDTH, width + "");
    }

    public int getHeight() {
        return getPropIntValue(FORM_FIELD_HEIGHT);
    }

    public void setHeight(int height) {
        setPropValue(FORM_FIELD_HEIGHT, height + "");
    }

    public DisplayStyle getDisplayStyle() {
        return DisplayStyle.getStyle(getPropStringValue(FORM_FIELD_DISPLAY_STYLE));
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        setPropValue(FORM_FIELD_DISPLAY_STYLE, displayStyle.name());
    }

    /*@XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }*/

    public int getSortNum() {
        return getPropIntValue(FORM_FIELD_SORT_NUM);
    }

    public void setSortNum(int sortNum) {
        setPropValue(FORM_FIELD_SORT_NUM, sortNum + "");
    }

    /*@XmlTransient
    public MetaForm getForm() {
        return form;
    }

    public void setForm(MetaForm form) {
        this.form = form;
    }*/

    public MetaField getMetaField() {
        return metaField;
    }

    public void setMetaField(MetaField metaField) {
        this.metaField = metaField;
    }
}
