package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
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
public class MuDate extends BaseFormField implements IValue {
    private DatePicker datePicker;

    public MuDate(FormFieldProperty property) {
        this(property, false);
    }

    public MuDate(FormFieldProperty property, boolean isAddQueryModel) {
        super(property);
        this.isAddQueryMode = isAddQueryModel;
        init();
    }

    @Override
    protected void initPrep() {
        datePicker = new DatePicker();
        datePicker.setPrefWidth(config.getWidth());
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return UDate.dateToString(date);
            }

            @Override
            public LocalDate fromString(String date) {
                return UDate.toLocalDate(date);
            }
        });
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                valueProperty().set(UDate.dateToString(newValue));
            }
        });
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{datePicker};
    }

    @Override
    public String value() {
        if (datePicker.getValue() == null) {
            return null;
        }
        return UDate.dateToString(datePicker.getValue());
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (UString.isNotEmpty(value)) {
            datePicker.setValue(UDate.toLocalDate(value));
        }
    }
}
