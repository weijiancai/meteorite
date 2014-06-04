package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * MetaUI 文本输入域
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuTextArea extends BaseFormField implements IValue {
    private TextArea textArea;

    public MuTextArea(FormFieldProperty property) {
        super(property);
        init();
    }

    @Override
    protected void initPrep() {
        textArea = new TextArea();
        this.setPrefHeight(config.getHeight());
        textArea.prefWidthProperty().bind(this.widthProperty());
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                valueProperty().set(newValue);
            }
        });
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{textArea};
    }

    @Override
    public String value() {
        return textArea.getText();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        textArea.setText(value);
    }
}
