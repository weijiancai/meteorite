package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UString;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * 整数范围表单控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuIntRange extends BaseFormField {
    private MuTextField startField;
    private MuTextField endField;

    public MuIntRange(FormFieldProperty property) {
        super(property);
        this.isAddQueryMode = false;
        init();
    }

    @Override
    protected void initPrep() {
        startField = new MuTextField(config, false);
        endField = new MuTextField(config, false);
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
            list.add(new Condition(config.getMetaField().getOriginalName(), QueryModel.GREATER_EQUAL, startField.value(), MetaDataType.INTEGER));
        }
        if (UString.isNotEmpty(endField.value())) {
            list.add(new Condition(config.getMetaField().getOriginalName(), QueryModel.LESS_THAN, endField.value(), MetaDataType.INTEGER));
        }
        return list;
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{startField, new Label("至"), endField};
    }
}
