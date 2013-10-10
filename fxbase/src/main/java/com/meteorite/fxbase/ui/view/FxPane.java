package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.ui.IPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * JavaFx 面板
 *
 * @author wei_jc
 * @since  1.0.0
 */
public abstract class FxPane extends BorderPane implements IPane {
    protected ILayoutConfig layoutConfig;
    protected boolean isShowTop = true;

    public FxPane(ILayoutConfig layoutConfig, boolean isShowTop) {
        this.layoutConfig = layoutConfig;

        if(isShowTop) {
            this.setTop(createTop());
        }
        this.setCenter(createCenter());
    }

    @Override
    public void setShowTop(boolean isShow) {
        isShowTop = isShow;
    }

    /**
     * 创建Top Pane
     *
     * @return 返回Top Pane
     */
    protected abstract Pane createTop();

    /**
     * 创建Center Pane
     *
     * @return 返回Center Pane
     */
    protected abstract Pane createCenter();
}
