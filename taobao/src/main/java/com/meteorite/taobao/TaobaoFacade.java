package com.meteorite.taobao;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.core.util.UtilFile;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoFacade extends BaseFacade {
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "taobao";

    private static TaobaoFacade instance;

    private TaobaoFacade() {

    }

    @Override
    protected void initProjectConfig() throws Exception {
        SystemManager factory = SystemManager.getInstance();
        projectConfig = factory.getProjectConfig(PROJECT_NAME);
        if (projectConfig == null) {
            projectConfig = factory.createProjectConfig(PROJECT_NAME);
            projectConfig.setDisplayName("淘宝助手");
            DataSource taobaoDS = new DataSource(PROJECT_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "");
            taobaoDS.setFilePath(UtilFile.createFile(projectConfig.getHsqldbDir(), PROJECT_NAME).getAbsolutePath());
            projectConfig.getDataSources().add(taobaoDS);

            factory.save(projectConfig);
            HSqlDBServer.getInstance().addDbFile(PROJECT_NAME, taobaoDS.getFilePath());
        }
    }

    public static TaobaoFacade getInstance() {
        if (instance == null) {
            instance = new TaobaoFacade();
        }
        return instance;
    }
}
