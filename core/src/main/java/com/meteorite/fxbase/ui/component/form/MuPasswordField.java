package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;

/**
 * MetaUI 密码输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuPasswordField extends BaseFormField implements IValue {
    private PasswordField passwordField;

    public MuPasswordField(FormFieldProperty property) {
        super(property);
        init();
    }

    @Override
    protected void initPrep() {
        passwordField = new PasswordField();
        passwordField.setPrefWidth(config.getWidth());
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{passwordField};
    }

    @Override
    public String value() {
        return passwordField.getText();
    }

    @Override
    public void setValue(String value) {
        passwordField.setText(value);
    }

}
