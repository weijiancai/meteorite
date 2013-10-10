package com.meteorite.fxbase.ui.config;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormFieldConfig extends FormFieldConfig {
    private StringProperty nameProperty = new SimpleStringProperty();
    private StringProperty displayNameProperty = new SimpleStringProperty();

    public FxFormFieldConfig(FormConfig formConfig, ILayoutConfig config) {
        super(formConfig, config);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void setName(String name) {
        super.setName(name);
        nameProperty.set(name);
    }

    public StringProperty displayNameProperty() {
        return displayNameProperty;
    }

    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
        displayNameProperty.set(displayName);
    }
}
