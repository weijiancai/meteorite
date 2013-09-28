package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VTextField extends HBox implements IValue {
    private TextField textField;

    public VTextField(final ILayoutConfig layoutConfig, boolean isDesign) {
        textField = new TextField();
        textField.prefWidthProperty().bindBidirectional(this.prefWidthProperty());
        this.getChildren().add(textField);

        if (isDesign) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textField.fireEvent(new FxLayoutEvent(layoutConfig));
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
        return textField.getText().trim();
    }

    @Override
    public void setValue(String[] value) {
        if (value != null && value.length > 0) {
            textField.setText(value[0]);
        }
    }

    @Override
    public void setValue(String value) {
        setValue(new String[]{value});
    }
}
