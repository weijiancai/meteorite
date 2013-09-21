package com.meteorite.core.ui.config.layout;

import com.meteorite.core.ui.ILayoutConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FormConfig extends BaseLayoutConfig {
    private List<FormFieldConfig> formFields = new ArrayList<FormFieldConfig>();

    public FormConfig(ILayoutConfig config) {
        super(config);

        for (ILayoutConfig layoutConfig : config.getChildren()) {
            formFields.add(new FormFieldConfig(this, layoutConfig));
        }
    }

    public String getName() {
        return getStringValue(FORM_NAME);
    }

    public void setName(String name) {
        setValue(FORM_NAME, name);
    }

    public String getDisplayName() {
        return getStringValue(FORM_DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) {
        setValue(FORM_DISPLAY_NAME, displayName);
    }

    public String getFormType() {
        return getStringValue(FORM_TYPE);
    }

    public void setFormType(String formType) {
        setValue(FORM_TYPE, formType);
    }

    public int getColCount() {
        return getIntValue(FORM_COL_COUNT);
    }

    public void setColCount(int colCount) {
        setValue(FORM_COL_COUNT, colCount + "");
    }

    public int getColWidth() {
        return getIntValue(FORM_COL_WIDTH);
    }

    public void setColWidth(int colWidth) {
        setValue(FORM_COL_WIDTH, colWidth + "");
    }

    public int getLabelGap() {
        return getIntValue(FORM_LABEL_GAP);
    }

    public void setLabelGap(int labelGap) {
        setValue(FORM_LABEL_GAP, labelGap + "");
    }

    public int getFieldGap() {
        return getIntValue(FORM_FIELD_GAP);
    }

    public void setFieldGap(int fieldGap) {
        setValue(FORM_FIELD_GAP, fieldGap + "");
    }

    public int getHgap() {
        return getIntValue(FORM_HGAP);
    }

    public void setHgap(int hgap) {
        setValue(FORM_HGAP, hgap + "");
    }

    public int getVgap() {
        return getIntValue(FORM_VGAP);
    }

    public void setVgap(int vgap) {
        setValue(FORM_VGAP, vgap + "");
    }

    public List<FormFieldConfig> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldConfig> formFields) {
        this.formFields = formFields;
    }

    /*public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }*/
}
