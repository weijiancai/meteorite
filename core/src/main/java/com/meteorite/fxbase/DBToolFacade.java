package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.core.util.UFile;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.view.MUTabsDesktop;

/**
 * 数据库工具Facade
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DBToolFacade extends BaseFacade {
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "DBTool";
    private static DBToolFacade instance;

    private ProjectConfig projectConfig;

    private DBToolFacade() {

    }

    @Override
    protected void initProjectConfig() throws Exception {
        projectConfig = SystemManager.getInstance().getProjectConfig(PROJECT_NAME);
        if (projectConfig == null) {
            projectConfig = SystemManager.getInstance().createProjectConfig(PROJECT_NAME);
        }
    }

    public static DBToolFacade getInstance() {
        if (instance == null) {
            instance = new DBToolFacade();
        }
        return instance;
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
        return new MUTabsDesktop(DataSourceManager.getNavTree());
    }
}
