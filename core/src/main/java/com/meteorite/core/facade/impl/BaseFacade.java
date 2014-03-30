package com.meteorite.core.facade.impl;

import com.meteorite.core.facade.IFacade;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFacade implements IFacade {
    protected BaseFacade() {
        try {
            initializeFacade();
        } catch (Exception e) {
            e.printStackTrace();
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
