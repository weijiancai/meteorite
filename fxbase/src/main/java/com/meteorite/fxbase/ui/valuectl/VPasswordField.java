package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VPasswordField extends HBox implements IValue {
    private PasswordField passwordField;

    public VPasswordField(final ILayoutConfig layoutConfig, boolean isDesign) {
        passwordField = new PasswordField();
        this.getChildren().add(passwordField);
        HBox.setHgrow(passwordField, Priority.ALWAYS);

        if (isDesign) {
            passwordField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    passwordField.fireEvent(new FxLayoutEvent(layoutConfig));
                }
            });
        }
    }

    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        return passwordField.getText().trim();
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            passwordField.setText(value[0]);
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
