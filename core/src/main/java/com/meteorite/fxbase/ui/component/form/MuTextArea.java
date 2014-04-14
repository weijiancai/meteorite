package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.TextArea;

/**
 * MetaUI 文本输入域
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuTextArea extends TextArea implements IValue {

    public MuTextArea(FormFieldProperty fieldConfig) {
        this.setPrefHeight(fieldConfig.getHeight());
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
