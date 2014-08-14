package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期范围 控件
 *
 * @author weijiancai
 * @since 1.0.0
 */
public class MuDateRange extends BaseFormField implements IValue {
    private MuDate startField;
    private MuDate endField;

    public MuDateRange(FormFieldProperty property) {
        super(property);

        /*startField.prefWidthProperty().bind(this.prefWidthProperty().divide(2).add(-10));
        endField.prefWidthProperty().bind(this.prefWidthProperty().divide(2).add(-10));*/
        this.isAddQueryMode = false;
        init();
    }

    @Override
    protected void initPrep() {
        startField = new MuDate(config, false);
        endField = new MuDate(config, false);
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public void setValue(String value) {
    }

    @Override
    public List<Condition> getConditions() {
        List<Condition> list = new ArrayList<Condition>();

        if (UString.isNotEmpty(startField.value())) {
            list.add(new Condition(config.getColumnName(), QueryModel.GREATER_EQUAL, startField.value(), MetaDataType.DATE));
        }
        if (UString.isNotEmpty(endField.value())) {
            list.add(new Condition(config.getColumnName(), QueryModel.LESS_THAN, endField.value(), MetaDataType.DATE));
        }
        return list;
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{startField, new Label("至"), endField};
    }
}
