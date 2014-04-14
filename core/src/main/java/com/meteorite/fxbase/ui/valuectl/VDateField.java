package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VDateField extends DatePicker implements IValue {
    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        return this.getValue().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            this.setValue(LocalDate.parse(value[0]));
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
