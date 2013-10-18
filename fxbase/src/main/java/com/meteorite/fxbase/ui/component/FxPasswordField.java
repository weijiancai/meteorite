package com.meteorite.fxbase.ui.component;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

/**
 * JavaFx 密码输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxPasswordField extends FxFormField {
    private PasswordField passwordField;

    public FxPasswordField(FxFormFieldConfig fieldConfig) {
        super(fieldConfig);

        passwordField = new PasswordField();
        passwordField.setPrefWidth(fieldConfig.getWidth());
    }

    @Override
    public void setDisplayStyle(DisplayStyle displayStyle) {
    }

    @Override
    public Node getNode() {
        return passwordField;
    }

    @Override
    public void setValue(String... values) {
        passwordField.setText(values[0]);
    }

    @Override
    public String getValue() {
        return passwordField.getText();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return passwordField.textProperty();
    }

    @Override
    public DoubleProperty widthProperty() {
        return passwordField.prefWidthProperty();
    }

    @Override
    public DoubleProperty heightProperty() {
        return passwordField.prefHeightProperty();
    }

    @Override
    public void registLayoutEvent() {
        passwordField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                passwordField.fireEvent(new FxLayoutEvent(fieldConfig.getLayoutConfig(), FxPasswordField.this));
            }
        });
    }
}
