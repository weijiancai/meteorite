package com.meteorite.core.facade.impl;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.ui.layout.LayoutConfigManager;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseFacade implements IFacade {
    protected ProjectConfig projectConfig;

    protected BaseFacade()
    {
        initializeFacade();
    }

    private void initializeFacade() {
        // 1. 初始化ProjectConfig
        initProjectConfig();
        // 2. 加载Layout
        LayoutConfigManager.load();
    }

    @Override
    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    /**
     * 初始化ProjectConfig
     */
    protected abstract void initProjectConfig();
}
