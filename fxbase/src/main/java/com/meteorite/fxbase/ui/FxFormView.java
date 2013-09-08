package com.meteorite.fxbase.ui;

import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.layout.FxFormLayout;
import javafx.scene.layout.BorderPane;

/**
 * JavaFx表单视图
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView extends BorderPane {
    private FxFormLayout layout;

    public FxFormView(FormProperty formProperty) {
        layout = new FxFormLayout(formProperty);
        this.setCenter(layout.layout());
    }

    public FxFormView(FxFormLayout layout) {

    }

    public void initUI() {

    }

    public FxFormLayout getLayout() {
        return layout;
    }

    public <UI> UI getUI() {
        return null;
    }
}
