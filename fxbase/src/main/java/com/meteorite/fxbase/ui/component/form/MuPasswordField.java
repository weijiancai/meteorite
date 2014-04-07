package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import javafx.scene.control.PasswordField;

/**
 * MetaUI 密码输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuPasswordField extends PasswordField implements IValue {
    public MuPasswordField(FormFieldProperty fieldConfig) {
        this.setPrefWidth(fieldConfig.getWidth());
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
    public void setValue(String... values) {
        this.setText(values[0]);
    }

    @Override
    public void setValue(String value) {
        setText(value);
    }

}
