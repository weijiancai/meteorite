package com.meteorite.fxbase.ui.view;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.fxbase.ui.IFormField;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    public FxFormField(FxFormFieldConfig fieldConfig) {
        this.fieldConfig = fieldConfig;

        fieldConfig.displayNameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                getLabel().setText(newValue);
            }
        });
        fieldConfig.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                widthProperty().set(newValue.doubleValue());
            }
        });
        fieldConfig.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                heightProperty().set(newValue.doubleValue());
            }
        });
        fieldConfig.displayStyleProperty().addListener(new ChangeListener<DisplayStyle>() {
            @Override
            public void changed(ObservableValue<? extends DisplayStyle> observable, DisplayStyle oldValue, DisplayStyle newValue) {
                setDisplayStyle(newValue);
            }
        });
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
