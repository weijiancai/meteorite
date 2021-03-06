package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.layout.MUFormLayout;
import javafx.scene.layout.Pane;

import java.util.Map;

/**
 * MetaUI Form
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuQuery extends Pane {
    private FormProperty formConfig;
    private MUFormLayout layout;

    public MuQuery(FormProperty property) {
        this.formConfig = property;
        layout = new MUFormLayout(property);
        this.getChildren().add(layout);
    }

    public void setValues(DataMap result) {
        if (result == null) {
            return;
        }
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            entry.getValue().setValue(result.getString(entry.getKey()));
        }
    }
}
