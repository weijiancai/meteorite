package com.meteorite.core.web;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.core.util.UFile;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class CoreFacade extends BaseFacade {
    /**
     * 项目名称
     */
    public static final String PROJECT_NAME = "core";

    private ProjectConfig projectConfig;

    private static CoreFacade instance;

    private CoreFacade() {

    }

    @Override
    protected void initProjectConfig() throws Exception {
        SystemManager factory = SystemManager.getInstance();
        projectConfig = factory.getProjectConfig(PROJECT_NAME);
        if (projectConfig == null) {
            projectConfig = factory.createProjectConfig(PROJECT_NAME);
            projectConfig.setDisplayName("基础类库");
            DBDataSource coreDS = new DBDataSource(PROJECT_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/core", "sa", "", "1.0.0");
            coreDS.setFilePath(UFile.createFile(projectConfig.getHsqldbDir(), PROJECT_NAME).getAbsolutePath());
            projectConfig.getDataSources().add(coreDS);

            factory.save(projectConfig);
            HSqlDBServer.getInstance().addDbFile(PROJECT_NAME, coreDS.getFilePath());
        }
    }

    public static CoreFacade getInstance() {
        if (instance == null) {
            instance = new CoreFacade();
        }
        return instance;
    }

    @Override
    public ProjectConfig getProjectConfig() throws Exception {
        return projectConfig;
    }

    @Override
    public void initAfter() throws Exception {
        // 初始化数据库表
        /*DBConnection conn = DBManager.getConnection(projectConfig.getDataSources().get(0));
        File initSqlFile = new File(getClass().getResource("/dbversion/taobao1.0.0.sql").toURI());
        conn.execSqlFile(initSqlFile);*/

        // 插入淘宝分类数据
        /*TbItemCat root = new TbItemCat();
        root.setCid(0L);
        TbItemFacade facade = new TbItemFacade(AppKeyFactory.getZt().getClient(), AppKeyFactory.getZt().sessionKey);
        facade.getItemCats(root);*/
    }

    @Override
    public Parent getDesktop() {
        return null;
    }
}
