package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VTextArea extends HBox implements IValue {
    private TextArea textArea;

    public VTextArea() {
        textArea = new TextArea();
        this.getChildren().add(textArea);
    }

    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        return textArea.getText().trim();
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            textArea.setText(value[0]);
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
