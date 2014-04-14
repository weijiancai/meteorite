package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.TextField;

/**
 * MetaUI 文本输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuTextField extends TextField implements IValue {

    public MuTextField(FormFieldProperty fieldConfig) {
        this.setPrefWidth(fieldConfig.getWidth());
        this.setText(fieldConfig.getValue());
    }

    @Override
    public String[] values() {
        return new String[0];
    }

    @Override
    public String value() {
        return getText();
    }

    @Override
    public void setValue(String[] value) {

    }

    @Override
    public void setValue(String value) {
        setText(value);
    }
}
