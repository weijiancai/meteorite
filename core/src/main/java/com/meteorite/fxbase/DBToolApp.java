package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.facade.impl.BaseFacade;

/**
 * 数据库工具App
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBToolApp extends BaseApp {
    @Override
    protected IFacade getFacade() {
        return new BaseFacade() {
            @Override
            protected void initProjectConfig() throws Exception {

            }

            @Override
            public ProjectConfig getProjectConfig() throws Exception {
                return SystemManager.getInstance().createProjectConfig("DBTool");
            }

            @Override
            public void initAfter() throws Exception {

            }
        };
    }

    public static void main(String[] args) {
        launch(DBToolApp.class);
    }
}
