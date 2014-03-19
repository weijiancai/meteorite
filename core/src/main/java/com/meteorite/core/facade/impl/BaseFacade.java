package com.meteorite.core.facade.impl;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.ui.layout.LayoutManager;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseFacade implements IFacade {
    protected ProjectConfig projectConfig;

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
        // 2. 加载Layout
        LayoutManager.load();
    }

    @Override
    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    /**
     * 初始化ProjectConfig
     */
    protected abstract void initProjectConfig() throws Exception;
}
