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
public class MetaInit {
    private static JdbcTemplate template;

    public static void init(JdbcTemplate template) throws Exception {
        MetaInit.template = template;
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("category", "code_AirManager");
        template.delete(conditionMap, "mu_meta");

        // 国内 -- 柜台岗 -- 收费与账单 -- 国内进港邮航包机收费
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

        // 国内 -- 查询岗 -- 国内进港 -- 不正常
        // 不正常货物查询 -- 查询条件
        save(new MetaItem("运单号"));
        save(new MetaItem("不正常类型"));
        save(new MetaItem("登记日期"));
        save(new MetaItem("航班号"));
        save(new MetaItem("是否结案"));
        save(new MetaItem("特货代码"));
        save(new MetaItem("营业点"));
        save(new MetaItem("包含归档运单"));
        // 不正常货物查询 -- 表格
        save(new MetaItem("件数"));
        save(new MetaItem("重量"));
        save(new MetaItem("业务数据"));
        save(new MetaItem("费率"));
        save(new MetaItem("费用"));

        save(new MetaItem("结算方式"));
        save(new MetaItem("总件数"));
        save(new MetaItem("总重量"));
    }

    private static void save(MetaItem item) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 初始化元数据项
            MetaInit.init(template);

            template.commit();
        } finally {
            template.close();
        }
    }
}
