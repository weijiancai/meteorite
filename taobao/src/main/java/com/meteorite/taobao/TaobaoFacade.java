package com.meteorite.taobao;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.facade.impl.BaseFacade;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoFacade extends BaseFacade {
    private static TaobaoFacade instance;

    private ProjectConfig projectConfig;

    private TaobaoFacade() {
        projectConfig = ProjectConfigFactory.getProjectConfig(".taobao");
        DataSource taobaoDS = new DataSource("taobao", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "");
        projectConfig.getDataSources().add(taobaoDS);
        try {
            ProjectConfigFactory.save(projectConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public static TaobaoFacade getInstance() {
        if (instance == null) {
            instance = new TaobaoFacade();
        }
        return instance;
    }
}
