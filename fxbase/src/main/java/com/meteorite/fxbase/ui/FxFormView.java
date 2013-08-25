package com.meteorite.fxbase.ui;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.IFormView;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.layout.FxFormLayout;
import com.meteorite.fxbase.ui.valuectl.VPasswordField;
import com.meteorite.fxbase.ui.valuectl.VTextArea;
import com.meteorite.fxbase.ui.valuectl.VTextField;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * JavaFx表单视图
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView extends BorderPane implements IFormView<FxFormLayout> {
    private FxFormLayout layout;

    public FxFormView(FormProperty formProperty) {
        layout = new FxFormLayout(formProperty);
        this.setCenter(layout.layout());
    }

    @Override
    public void initUI() {

    }

    @Override
    public FxFormLayout getLayout() {
        return layout;
    }
}
