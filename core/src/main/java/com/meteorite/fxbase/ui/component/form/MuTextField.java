package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * MetaUI 文本输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuTextField extends BaseFormField implements IValue {
    private TextField textField;

    public MuTextField(FormFieldProperty property) {
        super(property);
        init();
    }

    public MuTextField(FormFieldProperty property, boolean isAddQueryModel) {
        super(property);
        this.isAddQueryMode = isAddQueryModel;
        init();
    }

    @Override
    protected void initPrep() {
        textField = new TextField();
        textField.prefWidthProperty().bind(this.widthProperty());
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                valueProperty().set(newValue);
            }
        });
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{textField};
    }

    @Override
    public String value() {
        return textField.getText();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        textField.setText(value);
    }
}
