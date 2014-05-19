package com.meteorite.core.facade.impl;

import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.ui.view.MUDialog;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFacade implements IFacade {
    protected BaseFacade() {
        try {
            initializeFacade();
        } catch (Exception e) {
            MUDialog.showExceptionDialog(e);
        }
    }

    private void initializeFacade() throws Exception {
        // 1. 初始化ProjectConfig
        initProjectConfig();
    }

    /**
     * 初始化ProjectConfig
     */
    protected abstract void initProjectConfig() throws Exception;
}
