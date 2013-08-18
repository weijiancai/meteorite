package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.util.JAXBUtil;
import com.meteorite.core.util.UString;

import java.io.File;

/**
 * @author wei_jc
 * @version 1.0
 */
public class ProjectConfigFactory {
    private static ProjectConfig projectConfig;

    private static void initProjectConfig() {
        Class<?>[] clazz = new Class[]{ProjectConfig.class, DataSource.class};
        File file = ProjectConfig.getProjectConfigFile();
        try {
            if (!file.exists()) {
                JAXBUtil.marshalToFile(projectConfig, file, clazz);
            }
            projectConfig = JAXBUtil.unmarshal(file, ProjectConfig.class);

            /*boolean hasSysDataSource = false;
            for (DataSource dataSource : projectConfig.getDataSources()) {
                if ("sys".equals(dataSource.getName())) {
                    hasSysDataSource = true;
                    break;
                }
            }
            if (!hasSysDataSource) {
                projectConfig.getDataSources().add(DBManager.getSysDataSource());
                save(projectConfig);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProjectConfig getProjectConfig(String projectName) {
        if (projectConfig == null) {
            projectConfig = new ProjectConfig(projectName);
            initProjectConfig();
        }
        return projectConfig;
    }

    public static void save(ProjectConfig projectConfig) throws Exception {
        JAXBUtil.marshalToFile(projectConfig, ProjectConfig.getProjectConfigFile(), ProjectConfig.class, DataSource.class);
    }

    /**
     * 检查项目是否已经配置
     * 1. 检查项目名称
     * 2. 检查sys数据源
     *
     * @param projectConfig 项目配置信息
     * @return 如果已经配置，返回true，否则返回false
     */
    public static boolean isConfigured(ProjectConfig projectConfig) {
        // 检查项目名称
        if (UString.isEmpty(projectConfig.getProjectName())) {
            return false;
        }
        // 检查sys数据源
        boolean hasSysDataSource = false;
        for (DataSource dataSource : projectConfig.getDataSources()) {
            if ("sys".equals(dataSource.getName())) {
                hasSysDataSource = true;
                break;
            }
        }
        if (!hasSysDataSource) {
            return false;
        }
        return true;
    }
}
