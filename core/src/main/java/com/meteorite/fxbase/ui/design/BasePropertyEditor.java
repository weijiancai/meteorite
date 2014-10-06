package com.meteorite.fxbase.ui.design;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BasePropertyEditor<T, C extends Node> implements PropertyEditor<T> {
    private final PropertySheet.Item property;
    private final C control;

    public BasePropertyEditor(PropertySheet.Item property, C control) {
        this(property, control, !property.isEditable());
    }

    public BasePropertyEditor(PropertySheet.Item property, C control, boolean readonly) {
        this.control = control;
        this.property = property;
        if(!readonly) {
            this.getObservableValue().addListener(new ChangeListener<T>() {
                @Override
                public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                    BasePropertyEditor.this.property.setValue(BasePropertyEditor.this.getValue());
                }
            });
        }

    }

    protected abstract ObservableValue<T> getObservableValue();

    public final PropertySheet.Item getProperty() {
        return this.property;
    }

    public C getEditor() {
        return this.control;
    }

    public T getValue() {
        return this.getObservableValue().getValue();
    }

}
