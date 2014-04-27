package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.fxbase.ui.view.MUTabsDesktop;
import javafx.scene.Node;
import javafx.scene.Parent;

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

            @Override
            public Parent getDesktop() throws Exception {
                DataSource dataSource = DataSourceManager.getSysDataSource();
                return new MUTabsDesktop(dataSource.getNavTree());
            }
        };
    }

    public static void main(String[] args) {
        launch(DBToolApp.class);
    }
}
