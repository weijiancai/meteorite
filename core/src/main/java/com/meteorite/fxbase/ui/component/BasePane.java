package com.meteorite.fxbase.ui.component;

import javafx.scene.layout.BorderPane;

/**
 * MetaUI 基础面板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BasePane extends BorderPane implements IComponent {
    protected BasePane() {
        super();
    }

    @Override
    public void initPrep() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initAfter() {

    }

    @Override
    public void init() {
        initPrep();
        initUI();
        initAfter();
    }
}
