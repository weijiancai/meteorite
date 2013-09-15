package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.fxbase.ui.layout.FxLayoutFactory;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView implements IView<Pane> {
    private final IViewConfig viewConfig;

    public FxFormView(IViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    @Override
    public void initUI() {
    }

    @Override
    public IViewConfig getViewConfig() {
        return viewConfig;
    }

    @Override
    public Pane layout() {
        return FxLayoutFactory.create(viewConfig);
    }
}
