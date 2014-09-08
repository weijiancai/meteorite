package com.meteorite.core.project;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ProjectManager {
    private static Map<String, ProjectDefine> projectNameMap = new HashMap<String, ProjectDefine>();
    private static Map<String, ProjectDefine> projectIdMap = new HashMap<String, ProjectDefine>();
    private static Map<String, NavMenu> navMenuMap = new HashMap<String, NavMenu>();


    public static void load() throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            String sql = "SELECT * FROM mu_project_define";
            List<ProjectDefine> projects = template.query(sql, MetaRowMapperFactory.getProjectDefine());
            for (final ProjectDefine project : projects) {
                projectIdMap.put(project.getId(), project);
                projectNameMap.put(project.getName(), project);
            }

            // 查询导航菜单
            sql = "SELECT * FROM mu_nav_menu";
            List<NavMenu> navMenus = template.query(sql, MetaRowMapperFactory.getNavMenu());
            for (NavMenu navMenu : navMenus) {
                navMenuMap.put(navMenu.getId(), navMenu);
                ProjectDefine project = projectIdMap.get(navMenu.getProjectId());
                if (project != null) {
                    project.getNavMenus().add(navMenu);
                }
            }

            // 构造树形导航菜单
            for (NavMenu navMenu : navMenuMap.values()) {
                NavMenu parent = navMenuMap.get(navMenu.getPid());
                if (parent != null) {
                    parent.getChildren().add(navMenu);
                }
            }

            template.commit();
        } finally {
            template.close();
        }
    }

    /**
     * 根据项目名称获得项目信息
     *
     * @param projectName 项目名称
     * @return 返回项目信息
     */
    public static ProjectDefine getProjectByName(String projectName) {
        return projectNameMap.get(projectName);
    }
}
