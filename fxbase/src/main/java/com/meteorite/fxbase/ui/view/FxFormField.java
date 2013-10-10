package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.fxbase.ui.IFormField;
import javafx.scene.control.Label;

/**
 * JavaFx表单字段
 *
 * @author wei_jc
 * @since  1.0.0
 */
public abstract class FxFormField implements IFormField {
    protected Label label;
    protected FormFieldConfig fieldConfig;

    public FxFormField(FormFieldConfig fieldConfig) {
        this.fieldConfig = fieldConfig;
    }

    @Override
    public Label getLabel() {
        return label;
    }

    @Override
    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public FormFieldConfig getFormFieldConfig() {
        return fieldConfig;
    }
}
