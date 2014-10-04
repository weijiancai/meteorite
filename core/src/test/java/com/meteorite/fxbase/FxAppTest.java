package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.ui.IDesktop;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxAppTest extends BaseApp {
    @Override
    public IFacade getFacade() {
        return new IFacade() {
            @Override
            public ProjectConfig getProjectConfig() throws Exception {
                ProjectConfig projectConfig = new ProjectConfig();
                projectConfig.setName("JavaFx App Test");
                projectConfig.setDisplayName("JavaFx App Test");
                return projectConfig;
            }

            @Override
            public void initAfter() throws Exception {

            }

            @Override
            public IDesktop getDesktop() throws Exception {
                return new FxTestDesktop();
            }
        };
    }

    public static void main(String[] args) {
        launch(FxAppTest.class);
    }
}
