package com.meteorite.taobao;

import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.facade.impl.BaseFacade;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoFacade extends BaseFacade {
    private static TaobaoFacade instance;

    private TaobaoFacade() {

    }

    @Override
    protected void initProjectConfig() {
        projectConfig = ProjectConfigFactory.getProjectConfig(".taobao");
        projectConfig.setProjectCnName("淘宝助手");
        DataSource taobaoDS = new DataSource("taobao", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "");
        projectConfig.getDataSources().add(taobaoDS);
        try {
            ProjectConfigFactory.save(projectConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TaobaoFacade getInstance() {
        if (instance == null) {
            instance = new TaobaoFacade();
        }
        return instance;
    }
}
