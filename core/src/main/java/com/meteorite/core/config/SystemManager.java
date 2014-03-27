package com.meteorite.core.config;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.util.*;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.config.SystemConfig.*;

/**
 * @author wei_jc
 * @version 1.0
 */
public class SystemManager {
    private static SystemManager instance;
    private static SystemInfo sysInfo;
    private Map<String, ProjectConfig> cache = new HashMap<String, ProjectConfig>();
    private LayoutConfig layoutConfig;

    static {
        // 加载系统信息
        sysInfo = new SystemInfo();
        try {
            sysInfo.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }

        return instance;
    }

    /**
     * 初始化系统
     */
    public void init() throws Exception {
        loadDataSource();
        //  启动数据库
        HSqlDBServer.getInstance().start();
        checkDbVersion();
        // 加载数据字典
        DictManager.load();
        // 加载布局配置
        LayoutManager.load();
        // 加载元数据
        MetaManager.load();
        // 加载视图
        ViewManager.load();
        // 加载项目
        loadProjectConfig();
    }

    private void loadProjectConfig() throws Exception {
        // 遍历所有项目
        File[] files = SystemConfig.DIR_SYSTEM.listFiles();
        if (files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    File projectConfigFile = new File(file, FILE_NAME_PROJECT_CONFIG);
                    if (projectConfigFile.exists()) {
                        ProjectConfig projectConfig = JAXBUtil.unmarshal(projectConfigFile, ProjectConfig.class);
                        cache.put(projectConfig.getName(), projectConfig);
                    }
                }
            }
        }
    }

    /**
     * 加载数据源
     */
    private void loadDataSource() throws Exception {
        // 加载系统默认Hsqldb数据源
        File sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYS_DB_NAME);
        HSqlDBServer.getInstance().addDbFile(SYS_DB_NAME, sysDbFile.getAbsolutePath());
        DBManager.addDataSource(DBManager.getSysDataSource());

        for (ProjectConfig config : cache.values()) {
            List<DBDataSource> list = config.getDataSources();
            if (list != null) {
                for (DBDataSource dataSource : list) {
                    DBManager.addDataSource(dataSource);
                    // 如果存在hsqldb文件路径，则加载hsqldb数据库文件
                    if (dataSource.getFilePath() != null) {
                        HSqlDBServer.getInstance().addDbFile(dataSource.getName(), dataSource.getFilePath());
                    }
                }
            }
        }
    }

    private void checkDbVersion() throws Exception {
        // 获得数据库当前系统的最大版本号
        String maxVersion = DBManager.getSysDataSource().getMaxVersion(SystemConfig.SYS_NAME);
        DatabaseType dbType = DBManager.getSysDataSource().getDatabaseType();
        System.out.println("当前系统版本为：" + maxVersion + ", 数据库为：" + dbType.getDisplayName());
        // 获得升级目录下升级脚本
        for (File file : SystemConfig.DIR_DB_UPGRADE.listFiles()) {
            String[] names = file.getName().replace(".sql","").toLowerCase().split("_");
            String version = names[1];
            if(dbType.getName().toLowerCase().endsWith(names[2]) && maxVersion.compareTo(version) < 0) {
                System.out.println("检测到新版本需要升级：" + version);
                DBManager.getConnection(SystemConfig.SYS_DB_NAME).execSqlFile(file);
                System.out.println("升级完成");
            }
        }
    }

    public ProjectConfig createProjectConfig(String projectName) throws Exception {
        ProjectConfig projectConfig = new ProjectConfig(projectName);
        Class<?>[] clazz = new Class[]{ProjectConfig.class, DBDataSource.class};
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), clazz);

        return projectConfig;
    }

    public ProjectConfig getProjectConfig(String projectName) {
        return cache.get(projectName);
    }

    public List<ProjectConfig> getProjectConfigs() {
        return new ArrayList<>(cache.values());
    }

    public static void save(ProjectConfig projectConfig) throws Exception {
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), ProjectConfig.class, DBDataSource.class);
    }

    /**
     * 获得布局配置
     *
     * @return 返回布局配置
     */
    public LayoutConfig getLayoutConfig() {
        return layoutConfig;
    }

    /**
     * 检查项目是否已经配置
     * 1. 检查项目名称
     * 2. 检查sys数据源
     *
     * @param projectConfig 项目配置信息
     * @return 如果已经配置，返回true，否则返回false
     */
    public static boolean isConfigured(ProjectConfig projectConfig) throws Exception {
        // 检查项目名称
        /*if (UString.isEmpty(projectConfig.getName())) {
            return false;
        }
        // 检查sys数据源
        if (DBManager.getDataSource(SystemConfig.SYS_DB_NAME) == null) {
            return false;
        }
        // 检查版本表
        if (!DBUtil.existsTable(SystemConfig.SYS_DB_VERSION_TABLE_NAME)) {
            return false;
        }*/

        return true;
    }

    /**
     * 获得系统信息
     *
     * @return 返回系统信息
     */
    public static SystemInfo getSystemInfo() {
        return sysInfo;
    }
}
