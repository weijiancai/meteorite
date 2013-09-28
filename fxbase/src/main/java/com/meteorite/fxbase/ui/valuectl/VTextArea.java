package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VTextArea extends HBox implements IValue {
    private TextArea textArea;

    public VTextArea(final ILayoutConfig layoutConfig, boolean isDesign) {
        textArea = new TextArea();
        this.getChildren().add(textArea);

        if (isDesign) {
            textArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textArea.fireEvent(new FxLayoutEvent(layoutConfig));
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
