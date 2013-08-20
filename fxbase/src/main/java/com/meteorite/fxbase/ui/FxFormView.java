package com.meteorite.fxbase.ui;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaFormField;
import com.meteorite.core.ui.IFormView;
import com.meteorite.fxbase.ui.layout.FxFormLayout;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaFx表单视图
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView extends BorderPane implements IFormView<FxFormLayout> {
    private Meta meta;

    public FxFormView(Meta meta) {
        this.meta = meta;

        initUI();
    }

    @Override
    public void initUI() {
        FxFormLayout layout = getLayout();
        layout.setValues(meta);
        this.setCenter(layout.layout());
    }

    @Override
    public FxFormLayout getLayout() {
        return new FxFormLayout();
    }
}
