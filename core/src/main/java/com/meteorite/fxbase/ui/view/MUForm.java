package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import com.meteorite.fxbase.ui.layout.MUFormLayout;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MetaUI Form
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUForm extends Pane {
    private FormProperty formConfig;
    private MUFormLayout layout;

    public MUForm(FormProperty property) {
        this.formConfig = property;
        layout = new MUFormLayout(property);
        this.getChildren().add(layout);
    }

    public void setValues(DBResult result) {
        if (result == null) {
            return;
        }
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            entry.getValue().setValue(result.getString(entry.getKey()));
        }
    }

    public List<ICanQuery> getQueryList() {
        return layout.getQuerList();
    }

    public Map<String, IValue> getValueMap() {
        return layout.getValueMap();
    }
}
