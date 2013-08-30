package com.meteorite.fxbase.ui;

import com.meteorite.core.ui.ILayout;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ViewConfig;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxView implements IView {
    private ViewConfig viewConfig;

    public FxView(ViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    @Override
    public void initUI() {
    }

    @Override
    public ILayout getLayout() {
        return null;
    }

    @Override
    public Pane getUI() {
        return null;
    }
}
