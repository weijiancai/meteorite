package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.IValue;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListView;

/**
 * MetaUI ListView
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListView<T> extends ListView<T> implements IValue {
    private String name;

    @Override
    public String value() {
        return UObject.toString(this.getSelectionModel().getSelectedItem());
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public StringProperty valueProperty() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
