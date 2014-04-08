package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * MetaUI 日期控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuDate extends DatePicker implements IValue {
    public MuDate(FormFieldProperty property) {
        this.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            @Override
            public LocalDate fromString(String date) {
                return UDate.toLocalDate(date);
            }
        });
    }

    @Override
    public String[] values() {
        return new String[0];
    }

    @Override
    public String value() {
        return getValue().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public void setValue(String[] value) {

    }

    @Override
    public void setValue(String value) {
        if (UString.isNotEmpty(value)) {
            setValue(UDate.toLocalDate(value));
        }
    }
}
