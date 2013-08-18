package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.TextArea;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VTextArea extends TextArea implements IValue {
    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        return getText().trim();
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            setText(value[0]);
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
