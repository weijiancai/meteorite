package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
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
        textArea.setPrefHeight(config.getHeight());
        textArea.setPrefWidth(config.getWidth());
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
        textArea.setText(value);
    }
}
