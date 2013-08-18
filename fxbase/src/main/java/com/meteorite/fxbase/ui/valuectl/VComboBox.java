package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.fxbase.ui.IValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * @author weijiancai
 * @since 1.0.0
 */
public class VComboBox extends ComboBox<DictCode> implements IValue {
    private DictCategory category;

    public VComboBox(DictCategory category) {
        super();
        this.category = category;
        if (category != null) {
            List<DictCode> list = category.getCodeList();
            this.setItems(FXCollections.observableArrayList(list));
        }
    }

    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        DictCode code = this.getValue();
        return code == null ? "" : code.getValue();
    }

    @Override
    public void setValue(String[] value) {
    }

    @Override
    public void setValue(String value) {
        if (category != null) {
            for (DictCode code : this.getItems()) {
                if (code.getValue().equals(value)) {
                    this.getSelectionModel().select(code);
                }
            }
        }
    }
}
