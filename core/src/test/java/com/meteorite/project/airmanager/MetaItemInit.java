package com.meteorite.project.airmanager;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaItem;
import com.meteorite.core.util.Cn2Spell;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaItemInit {
    private static JdbcTemplate template;

    public static void init(JdbcTemplate template) throws Exception {
        MetaItemInit.template = template;
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("category", "code_AirManager");
        template.delete(conditionMap, "mu_meta_item");

        save(new MetaItem("航班号"));
        save(new MetaItem("流水号"));
        save(new MetaItem("包机单位"));

        save(new MetaItem("收费项目"));
        save(new MetaItem("关联业务"));
        save(new MetaItem("业务数据"));
        save(new MetaItem("费率"));
        save(new MetaItem("费用"));

        save(new MetaItem("结算方式"));
        save(new MetaItem("总件数"));
        save(new MetaItem("总重量"));
    }

    private static void save(MetaItem item) throws Exception {
        String spell = Cn2Spell.converterToFirstSpell(item.getDisplayName());
        item.setId("AM_" + spell);
        item.setCategory("code_AirManager");
        item.setName(spell);
        item.setDataType(MetaDataType.STRING);
        item.setValid(true);
        template.save(MetaPDBFactory.getMetaItem(item));
    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 初始化元数据项
            MetaItemInit.init(template);

            template.commit();
        } finally {
            template.close();
        }
    }
}
