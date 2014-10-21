package com.meteorite.core.project;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.project.tpl.CodeTpl;

import java.util.ArrayList;
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
    private static List<ProjectDefine> projects = new ArrayList<ProjectDefine>();

    public static void load() throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            String sql = "SELECT * FROM mu_project_define order by sort_num";
            List<ProjectDefine> projects = template.query(sql, MetaRowMapperFactory.getProjectDefine());
            for (final ProjectDefine project : projects) {
                putCache(project);
            }

            // 查询导航菜单
            sql = "SELECT * FROM mu_nav_menu order by level,sort_num";
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
                String pid = navMenu.getPid();
                NavMenu parent = navMenuMap.get(pid);
                if (parent != null) {
                    parent.getChildren().add(navMenu);
                }
                // 添加到根节点下
                if (pid != null && pid.equalsIgnoreCase("root")) {
                    projectIdMap.get(navMenu.getProjectId()).getRootNavMenu().getChildren().add(navMenu);
                }
            }

            // 查询代码模板
            sql = "SELECT * FROM mu_code_tpl order by sort_num";
            List<CodeTpl> tplList = template.query(sql, MetaRowMapperFactory.getCodeTpl());
            for (CodeTpl tpl : tplList) {
                ProjectDefine project = projectIdMap.get(tpl.getProjectId());
                if (project != null) {
                    project.getCodeTpls().add(tpl);
                }
            }

            template.commit();
        } finally {
            template.close();
        }
    }

    public static void putCache(ProjectDefine project) {
        projectIdMap.put(project.getId(), project);
        projectNameMap.put(project.getName(), project);
        projects.add(project);
        if (project.getRootNavMenu() == null) {
            NavMenu root = new NavMenu();
            root.setId("root");
            root.setName("root");
            root.setDisplayName("导航菜单");
            project.setRootNavMenu(root);
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

    /**
     * 根据项目ID获得项目信息
     *
     * @param projectId 项目ID
     * @return 返回项目信息
     */
    public static ProjectDefine getProjectById(String projectId) {
        return projectIdMap.get(projectId);
    }

    public static List<ProjectDefine> getProjects() {
        return projects;
    }
}
