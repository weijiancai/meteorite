package com.meteorite.core.config;

import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统导航
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SystemNavigation {
    private String id = "ROOT";
    private String displayName = "系统管理";
    private List<Object> children = new ArrayList<Object>();

    public SystemNavigation() throws Exception {
        Map<String, Object> metaManager = new HashMap<String, Object>();
        metaManager.put("id", "MetaManager");
        metaManager.put("displayName", "元数据管理");
        metaManager.put("action", "/view/meta_view");
        children.add(metaManager);

        Map<String, Object> projectManager = new HashMap<String, Object>();
        projectManager.put("id", "ProjectManager");
        projectManager.put("displayName", "项目管理");
        projectManager.put("children", SystemManager.getInstance().getProjectConfigs());
        children.add(projectManager);

        Map<String, Object> dataSourceManager = new HashMap<String, Object>();
        dataSourceManager.put("id", "DataSourceManager");
        dataSourceManager.put("displayName", "数据源管理");
        dataSourceManager.put("children", DataSourceManager.getNavTree());
        children.add(dataSourceManager);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Object> getChildren() {
        return children;
    }
}
