package com.meteorite.core.config;

import com.meteorite.core.db.DBManager;
import com.meteorite.core.db.DBUtil;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.util.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private Map<String, ProjectConfig> cache = new HashMap<String, ProjectConfig>();
    private LayoutConfig layoutConfig;

    private SystemManager() {
        try {
            loadProjectConfig();
            loadLayoutConfig();
            loadDataSource();
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
     * 加载布局配置信息
     */
    private void loadLayoutConfig() throws FileNotFoundException, JAXBException {
        layoutConfig = JAXBUtil.unmarshal(UIO.getInputStream(FILE_NAME_LAYOUT_CONFIG, UIO.FROM.CP), LayoutConfig.class);
    }

    /**
     * 加载数据源
     */
    private void loadDataSource() throws Exception {
        // 加载系统默认Hsqldb数据源
        File sysDbFile = UtilFile.createFile(DIR_SYSTEM_HSQL_DB, "sys");
        HSqlDBServer.getInstance().addDbFile("sys", sysDbFile.getAbsolutePath());
//        DBManager.addDataSource(DBManager.getSysDataSource());

        for (ProjectConfig config : cache.values()) {
            List<DataSource> list = config.getDataSources();
            if (list != null) {
                for (DataSource dataSource : list) {
                    DBManager.addDataSource(dataSource);
                    if (dataSource.getFilePath() != null) {
                        HSqlDBServer.getInstance().addDbFile(dataSource.getName(), dataSource.getFilePath());
                    }
                }
            }
        }
    }

    public ProjectConfig createProjectConfig(String projectName) throws Exception {
        ProjectConfig projectConfig = new ProjectConfig(projectName);
        Class<?>[] clazz = new Class[]{ProjectConfig.class, DataSource.class};
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), clazz);

        return projectConfig;
    }

    public ProjectConfig getProjectConfig(String projectName) {
        return cache.get(projectName);
    }

    public static void save(ProjectConfig projectConfig) throws Exception {
        JAXBUtil.marshalToFile(projectConfig, projectConfig.getProjectConfigFile(), ProjectConfig.class, DataSource.class);
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
}
