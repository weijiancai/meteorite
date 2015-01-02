package com.meteorite.project.nnairport;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.project.NavMenu;
import com.meteorite.core.util.Cn2Spell;
import com.meteorite.core.util.UUIDUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class NavMenuInit {
    private static JdbcTemplate template;
    private static int level1SortNum = 0;
    private static int level2SortNum = 0;
    private static int level3SortNum = 0;

    public static void init(JdbcTemplate template) throws Exception {
        NavMenuInit.template = template;

        NavMenu root = new NavMenu();
        root.setId("root");
        root.setDisplayName("ROOT");
        root.setLevel(0);

        addNavMenu(root, "机票");
        addNavMenu(root, "航班动态");
        addNavMenu(root, "行程服务");
        addNavMenu(root, "个人中心");
    }

    private static NavMenu addNavMenu(NavMenu parent, String displayName) throws Exception {
        String pinyin = Cn2Spell.converterToFirstSpell(displayName);
        /*String prefix = parent.getId();
        if (prefix.equals("root")) {
            prefix = "nav";
        } else if (prefix.startsWith("nav_")) {
            prefix = prefix.substring(4);
        }*/
        String id = UUIDUtil.getUUID();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("name", pinyin);
        param.put("display_name", displayName);
        param.put("pid", parent.getId());
        param.put("level", parent.getLevel() + 1);
        param.put("is_valid", "T");
        param.put("input_date", new Date());
        param.put("project_id", ProjectDefineInit.PROJECT_NAME);
        int sortNum = 0;
        if(parent.getLevel() == 0) {
            sortNum = level1SortNum += 10;
        } else if(parent.getLevel() == 1) {
            sortNum = level2SortNum += 10;
        } else if(parent.getLevel() == 2) {
            sortNum = level3SortNum += 10;
        }
        param.put("sort_num", sortNum);
        template.save(param, "mu_nav_menu");

        NavMenu menu = new NavMenu();
        menu.setId(id);
        menu.setLevel(parent.getLevel() + 1);

        return menu;
    }
}
