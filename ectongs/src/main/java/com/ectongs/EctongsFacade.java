package com.ectongs;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.fxbase.ui.IDesktop;

/**
 * 易诚通Facade
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsFacade extends BaseFacade {
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "Ectongs";
    private ProjectConfig projectConfig;

    @Override
    protected void initProjectConfig() throws Exception {
        projectConfig = SystemManager.getInstance().getProjectConfig(PROJECT_NAME);
        if (projectConfig == null) {
            projectConfig = SystemManager.getInstance().createProjectConfig(PROJECT_NAME);
        }
    }

    @Override
    public ProjectConfig getProjectConfig() throws Exception {
        return projectConfig;
    }

    @Override
    public void initAfter() throws Exception {

    }

    @Override
    public IDesktop getDesktop() throws Exception {
        return new EctongsDesktop();
    }
}
