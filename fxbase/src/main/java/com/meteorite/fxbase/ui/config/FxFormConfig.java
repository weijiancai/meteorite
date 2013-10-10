package com.meteorite.fxbase.ui.config;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFx 表单配置信息
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class FxFormConfig extends FormConfig {
    private List<FxFormFieldConfig> formFields = new ArrayList<>();

    private StringProperty nameProperty = new SimpleStringProperty();
    private StringProperty displayNameProperty = new SimpleStringProperty();
    private StringProperty formTypeProperty;
    private IntegerProperty colCountProperty;
    private IntegerProperty colWidthProperty;
    private IntegerProperty labelGapProperty;
    private IntegerProperty fieldGapProperty;

    public FxFormConfig(ILayoutConfig config) {
        super(config);
        for (ILayoutConfig layoutConfig : config.getChildren()) {
            formFields.add(new FxFormFieldConfig(this, layoutConfig));
        }
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void setName(String name) {
        super.setName(name);
        nameProperty.set(name);
    }

    public StringProperty displayNameProperty() {
        return displayNameProperty;
    }

    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
        displayNameProperty.set(displayName);
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

    /*public List<FormFieldConfig> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldConfig> formFields) {
        this.formFields = formFields;
    }*/

    public List<FxFormFieldConfig> getFxFormFields() {
        return formFields;
    }
}
