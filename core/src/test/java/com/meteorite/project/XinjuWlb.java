package com.meteorite.project;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.project.wlb.DictInit;
import com.meteorite.project.wlb.ProjectDefineInit;


/**
 * @author wei_jc
 * @since 1.0.0
 */
public class XinjuWlb {
    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 初始化项目定义
            ProjectDefineInit.init(template);
            // 初始化导航菜单
//            NavMenuInit.init(template);
            // 初始化数据字典
            DictInit.init(template);
            // 初始化元数据项
//            MetaItemInit.init(template);

            template.commit();
        } finally {
            template.close();
        }
    }
}
