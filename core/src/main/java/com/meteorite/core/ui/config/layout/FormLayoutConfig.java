package com.meteorite.core.ui.config.layout;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.config.LayoutConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FormLayoutConfig extends LayoutConfig {
    private List<FormFieldLayoutConfig> formFields = new ArrayList<FormFieldLayoutConfig>();

    public FormLayoutConfig(Meta meta) {
        this.setName(meta.getName() + "Form");
        this.setDisplayName(meta.getDisplayName() + "表单");
        for (MetaField field : meta.getFileds()) {
            formFields.add(new FormFieldLayoutConfig(field));
        }
    }

    public String getName() {
        return getPropStringValue(FORM_NAME);
    }

    public void setName(String name) {
        setPropValue(FORM_NAME, name);
    }

    public String getDisplayName() {
        return getPropStringValue(FORM_DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) {
        setPropValue(FORM_DISPLAY_NAME, displayName);
    }

    public String getFormType() {
        return getPropStringValue(FORM_TYPE);
    }

    public void setFormType(String formType) {
        setPropValue(FORM_TYPE, formType);
    }

    public int getColCount() {
        return getPropIntValue(FORM_COL_COUNT);
    }

    public void setColCount(int colCount) {
        setPropValue(FORM_COL_COUNT, colCount + "");
    }

    public int getColWidth() {
        return getPropIntValue(FORM_COL_WIDTH);
    }

    public void setColWidth(int colWidth) {
        setPropValue(FORM_COL_WIDTH, colWidth + "");
    }

    public int getLabelGap() {
        return getPropIntValue(FORM_LABEL_GAP);
    }

    public void setLabelGap(int labelGap) {
        setPropValue(FORM_LABEL_GAP, labelGap + "");
    }

    public int getFieldGap() {
        return getPropIntValue(FORM_FIELD_GAP);
    }

    public void setFieldGap(int fieldGap) {
        setPropValue(FORM_FIELD_GAP, fieldGap + "");
    }

    public int getHgap() {
        return getPropIntValue(FORM_HGAP);
    }

    public void setHgap(int hgap) {
        setPropValue(FORM_HGAP, hgap + "");
    }

    public int getVgap() {
        return getPropIntValue(FORM_VGAP);
    }

    public void setVgap(int vgap) {
        setPropValue(FORM_VGAP, vgap + "");
    }

    public List<FormFieldLayoutConfig> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldLayoutConfig> formFields) {
        this.formFields = formFields;
    }

    /*public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }*/
}
