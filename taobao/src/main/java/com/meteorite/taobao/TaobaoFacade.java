package com.meteorite.taobao;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.db.DBManager;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.db.object.DBConnection;
import com.meteorite.core.facade.impl.BaseFacade;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UtilFile;
import com.meteorite.taobao.api.TbItemFacade;

import java.io.File;

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
            DataSource taobaoDS = new DataSource(PROJECT_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "", "1.0.0");
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
}
