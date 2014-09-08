package com.meteorite.core.project;

import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.*;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

import static com.meteorite.core.config.SystemConfig.DIR_SYSTEM;
import static com.meteorite.core.config.SystemConfig.FILE_NAME_META_FIELD_CONFIG;

/**
 * 项目管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ProjectManager {
    private static Map<String, ProjectDefine> projectNameMap = new HashMap<String, ProjectDefine>();
    private static Map<String, ProjectDefine> projectIdMap = new HashMap<String, ProjectDefine>();


    public static void load() throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            String sql = "SELECT * FROM mu_project_define";
            List<ProjectDefine> projects = template.query(sql, MetaRowMapperFactory.getProjectDefine());
            for (final ProjectDefine project : projects) {
                projectIdMap.put(project.getId(), project);
                projectNameMap.put(project.getName(), project);
            }

            // 查询元数据引用
            sql = "SELECT * FROM mu_nav_menu";
            List<NavMenu> referenceList = template.query(sql, MetaRowMapperFactory.getNavMenu());
            for (NavMenu reference : referenceList) {

            }

            template.commit();
        } finally {
            template.close();
        }
    }

}
