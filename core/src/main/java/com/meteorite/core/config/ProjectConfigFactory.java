package com.meteorite.core.config;

import com.meteorite.core.db.DBManager;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.util.JAXBUtil;

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

            boolean hasSysDataSource = false;
            for (DataSource dataSource : projectConfig.getDataSources()) {
                if ("sys".equals(dataSource.getName())) {
                    hasSysDataSource = true;
                    break;
                }
            }
            if (!hasSysDataSource) {
                projectConfig.getDataSources().add(DBManager.getSysDataSource());
                save(projectConfig);
            }
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
}
