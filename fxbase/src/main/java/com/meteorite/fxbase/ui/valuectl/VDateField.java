package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.calendar.FXCalendar;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VDateField extends FXCalendar implements IValue {
    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        return this.getTextField().getText();
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            this.getTextField().setText(value[0]);
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
