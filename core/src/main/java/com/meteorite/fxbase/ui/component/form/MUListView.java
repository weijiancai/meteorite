package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.ICanInput;
import com.meteorite.fxbase.ui.ValueConverter;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaUI ListView
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListView<T> extends ListView<T> implements ICanInput<T> {

    // ======================== ICanInput ================================
    private String name;
    private ValueConverter<T> convert;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getInputValue() {
        return this.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setValueConvert(ValueConverter<T> convert) {
        this.convert = convert;
    }

    @Override
    public String getValueString() {
        if (convert != null) {
            List<String> list = new ArrayList<String>();
            for (T t : this.getSelectionModel().getSelectedItems()) {
                list.add(convert.toString(t));
            }
            return UString.convert(list);
        }

        return UString.convert(this.getSelectionModel().getSelectedItems());
    }

    public void setName(String name) {
        this.name = name;
    }
}
