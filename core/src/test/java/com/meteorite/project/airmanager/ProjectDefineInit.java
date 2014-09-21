package com.meteorite.project.airmanager;

import com.meteorite.core.datasource.db.util.JdbcTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ProjectDefineInit {
    public static final String PROJECT_NAME = "AirManager";

    public static void init(JdbcTemplate template) throws Exception {
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("id", PROJECT_NAME);
        template.delete(conditionMap, "mu_project_define");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", PROJECT_NAME);
        params.put("name", PROJECT_NAME);
        params.put("display_name", "航空货运系统");
        params.put("input_date", new Date());
        params.put("is_valid", "T");
        params.put("sort_num", 10);
        template.save(params, "mu_project_define");
    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 初始化项目定义
            ProjectDefineInit.init(template);

            template.commit();
        } finally {
            template.close();
        }
    }
}
