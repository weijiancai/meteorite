package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.calendar.FXCalendar;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 日期范围 控件
 *
 * @author weijiancai
 * @since 1.0.0
 */
public class VDateRangeField extends HBox implements IValue {
    private FXCalendar startField;
    private FXCalendar endField;

    public VDateRangeField() {
        super();

        startField = new FXCalendar();
        endField = new FXCalendar();
        startField.dateTextWidthProperty().bind(this.prefWidthProperty().divide(2).add(-10));
        endField.dateTextWidthProperty().bind(this.prefWidthProperty().divide(2).add(-10));

        this.getChildren().addAll(startField, new Label(" 至 "), endField);
    }

    @Override
    public String[] values() {
        String startText = startField.getTextField().getText();
        String endText = endField.getTextField().getText();
        if (UString.isNotEmpty(startText) && startText.trim().length() == 10) {
            startText += " 00:00:00";
        }
        if (UString.isNotEmpty(endText) && endText.trim().length() == 10) {
            endText += " 23:59:59";
        }

        return new String[]{startText, endText};
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public void setValue(String[] value) {
    }

    @Override
    public void setValue(String value) {
    }
}
