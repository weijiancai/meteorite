package com.meteorite.fxbase.ui.view;

import com.meteorite.core.R;
import com.meteorite.core.ui.config.ViewConfig;
import com.meteorite.core.ui.layout.LayoutConfigManager;
import com.meteorite.core.ui.model.Layout;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView {
    private ViewConfig config;

    public FxFormView(ViewConfig config) {
        this.config = config;
    }

    public void initUI() {
    }

    public Layout getLayout() {
        return LayoutConfigManager.getLayout(R.layout.FORM);
    }

    public Pane getUI() {
        return null;
    }
}
