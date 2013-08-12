package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.util.JAXBUtil;

import java.io.File;

/**
 * @author wei_jc
 * @version 1.0
 */
public class ProjectConfigFactory {
    private static ProjectConfig taobaoConfig = new ProjectConfig(".taobao");

    static {
        Class<?>[] clazz = new Class[]{ProjectConfig.class, DataSource.class};
        File file = ProjectConfig.getProjectConfigFile();
        try {
            if (!file.exists()) {
                JAXBUtil.marshalToFile(taobaoConfig, file, ProjectConfig.class, DataSource.class);
            }
            taobaoConfig = JAXBUtil.unmarshal(file, ProjectConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProjectConfig getTaobaoProjectConfig() {
        return taobaoConfig;
    }

    public static void save(ProjectConfig projectConfig) throws Exception {
        JAXBUtil.marshalToFile(taobaoConfig, ProjectConfig.getProjectConfigFile(), ProjectConfig.class, DataSource.class);
    }
}
