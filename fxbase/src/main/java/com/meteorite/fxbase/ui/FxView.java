package com.meteorite.fxbase.ui;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.ViewConfig;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxView {
    private ViewConfig viewConfig;

    public FxView(ViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    public void initUI() {
    }

    public ILayoutConfig getLayout() {
        return null;
    }

    public Pane getUI() {
        return null;
    }
}
