package com.meteorite.fxbase.ui.component;

import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.fxbase.ui.IFormField;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * JavaFx文本输入框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxTextField extends FxFormField {
    private TextField textField;

    public FxTextField(final FxFormFieldConfig fieldConfig) {
        super(fieldConfig);

        textField = new TextField();
        textField.setPrefWidth(fieldConfig.getWidth());
        textField.setText(fieldConfig.getValue());
//        textField.textProperty().bindBidirectional(fieldConfig.);
//        getLabel().textProperty().bindBidirectional(fieldConfig.displayNameProperty());
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                fieldConfig.setDisplayName(newValue);
            }
        });
    }

    @Override
    public Node getNode() {
        return textField;
    }

    @Override
    public void setValue(String... values) {
        textField.setText(values[0]);
    }

    @Override
    public String getValue() {
        return textField.getText().trim();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @Override
    public void registLayoutEvent() {
        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textField.fireEvent(new FxLayoutEvent<>(fieldConfig.getLayoutConfig(), this));
            }
        });
    }
}
