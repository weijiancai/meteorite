package com.meteorite.project;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.util.Cn2Spell;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class AirManager {
    private static JdbcTemplate template;
    private static int level1SortNum = 0;
    private static int level2SortNum = 0;

    private static final String PROJECT_NAME = "AirManager";

    public static void initProject(JdbcTemplate template) throws Exception {
        template.clearTable("mu_project_define");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", PROJECT_NAME);
        params.put("name", PROJECT_NAME);
        params.put("display_name", "航空货运系统");
        params.put("input_date", new Date());
        params.put("is_valid", "T");
        params.put("sort_num", 10);
        template.save(params, "mu_project_define");
    }

    public static void initNavMenu(JdbcTemplate template) throws Exception {
        AirManager.template = template;
        template.clearTable("mu_nav_menu");

        String level1 = addNavMenu("nav_", "安全管理", "root", "1");
        String prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "系统岗位", level1, "2");
        addNavMenu(prefix, "组织机构", level1, "2");
        addNavMenu(prefix, "系统用户", level1, "2");
        addNavMenu(prefix, "登陆信息", level1, "2");
        addNavMenu(prefix, "消息组配置", level1, "2");

        level1 = addNavMenu("nav_", "基础数据", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "异常代码", level1, "2");
        addNavMenu(prefix, "代理", level1, "2");
        addNavMenu(prefix, "物品编码", level1, "2");
        addNavMenu(prefix, "仓库位置", level1, "2");
        addNavMenu(prefix, "ULD信息", level1, "2");

        level1 = addNavMenu("nav_", "系统管理", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "用户管理", level1, "2");
        addNavMenu(prefix, "航班管理", level1, "2");
        addNavMenu(prefix, "场站管理", level1, "2");
        addNavMenu(prefix, "不正常管理", level1, "2");
        addNavMenu(prefix, "城市代码管理", level1, "2");

        level1 = addNavMenu("nav_", "国际出港", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "入库调度", level1, "2");
        addNavMenu(prefix, "收费处理", level1, "2");
        addNavMenu(prefix, "退仓处理", level1, "2");
        addNavMenu(prefix, "按航班收运入仓", level1, "2");
        addNavMenu(prefix, "收运入仓", level1, "2");
        addNavMenu(prefix, "运单收运入仓", level1, "2");
        addNavMenu(prefix, "简易制单", level1, "2");
        addNavMenu(prefix, "国际代单", level1, "2");
        addNavMenu(prefix, "变更申请", level1, "2");
        addNavMenu(prefix, "国际正式制单", level1, "2");
        addNavMenu(prefix, "运单文件", level1, "2");
        addNavMenu(prefix, "板箱申领", level1, "2");
        addNavMenu(prefix, "拉货补运", level1, "2");
        addNavMenu(prefix, "冷藏登记", level1, "2");
        addNavMenu(prefix, "中转接收", level1, "2");
        addNavMenu(prefix, "载量计划", level1, "2");
        addNavMenu(prefix, "代理人差错查询", level1, "2");
        addNavMenu(prefix, "航班文件", level1, "2");
        addNavMenu(prefix, "海关报文管理", level1, "2");
        addNavMenu(prefix, "中转换单", level1, "2");
        addNavMenu(prefix, "分单制表", level1, "2");
        addNavMenu(prefix, "分单拼单", level1, "2");
        addNavMenu(prefix, "AMS舱单", level1, "2");
        addNavMenu(prefix, "海关账册", level1, "2");

        level1 = addNavMenu("nav_", "国际进港", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "航班文件", level1, "2");
        addNavMenu(prefix, "分单录入理货", level1, "2");
        addNavMenu(prefix, "中转转港", level1, "2");
        addNavMenu(prefix, "联程货物统计", level1, "2");
        addNavMenu(prefix, "海关报文管理", level1, "2");
        addNavMenu(prefix, "海关账册", level1, "2");
        addNavMenu(prefix, "进港运单高级管理", level1, "2");
        addNavMenu(prefix, "清库查询", level1, "2");
        addNavMenu(prefix, "提货通知", level1, "2");
        addNavMenu(prefix, "提单", level1, "2");
        addNavMenu(prefix, "提货办单", level1, "2");
        addNavMenu(prefix, "介绍信", level1, "2");
        addNavMenu(prefix, "提货出库", level1, "2");
        addNavMenu(prefix, "运单状态", level1, "2");
        addNavMenu(prefix, "运单日志", level1, "2");
        addNavMenu(prefix, "不正常", level1, "2");
        addNavMenu(prefix, "航班查询", level1, "2");
        addNavMenu(prefix, "仓库管理", level1, "2");

        level1 = addNavMenu("nav_", "国内出港", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "收费处理", level1, "2");
        addNavMenu(prefix, "退仓处理", level1, "2");
        addNavMenu(prefix, "收运入仓", level1, "2");
        addNavMenu(prefix, "运单收运入仓", level1, "2");
        addNavMenu(prefix, "简易制单", level1, "2");
        addNavMenu(prefix, "国内正式制单", level1, "2");
        addNavMenu(prefix, "拉货补运", level1, "2");
        addNavMenu(prefix, "中转接收", level1, "2");
        addNavMenu(prefix, "始发站换单", level1, "2");
        addNavMenu(prefix, "出港运单高级管理", level1, "2");
        addNavMenu(prefix, "板箱复磅", level1, "2");
        addNavMenu(prefix, "地勤交接取消", level1, "2");
        addNavMenu(prefix, "航班预配操作", level1, "2");
        addNavMenu(prefix, "航班配载", level1, "2");
        addNavMenu(prefix, "拉货查询", level1, "2");

        level1 = addNavMenu("nav_", "国内进港", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "航班文件", level1, "2");
        addNavMenu(prefix, "中转转港", level1, "2");
        addNavMenu(prefix, "冷藏登记", level1, "2");
        addNavMenu(prefix, "联程货物统计", level1, "2");
        addNavMenu(prefix, "进港运单高级管理", level1, "2");
        addNavMenu(prefix, "中转换单", level1, "2");
        addNavMenu(prefix, "海关账册", level1, "2");
        addNavMenu(prefix, "清库查询", level1, "2");
        addNavMenu(prefix, "提货通知", level1, "2");
        addNavMenu(prefix, "提货办单", level1, "2");
        addNavMenu(prefix, "介绍信", level1, "2");
        addNavMenu(prefix, "不正常交付记录", level1, "2");
        addNavMenu(prefix, "提货出库", level1, "2");
        addNavMenu(prefix, "运单状态", level1, "2");
        addNavMenu(prefix, "运单日志", level1, "2");
        addNavMenu(prefix, "不正常", level1, "2");
        addNavMenu(prefix, "仓库管理", level1, "2");
        addNavMenu(prefix, "运单归档查询", level1, "2");
        addNavMenu(prefix, "航班查询", level1, "2");
        addNavMenu(prefix, "卸机比对", level1, "2");
        addNavMenu(prefix, "航班状态", level1, "2");
        addNavMenu(prefix, "计费业务数据维护", level1, "2");

        level1 = addNavMenu("nav_", "集控器材", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "机坪清点", level1, "2");
        addNavMenu(prefix, "空箱返回", level1, "2");
        addNavMenu(prefix, "备品出入库", level1, "2");
        addNavMenu(prefix, "板箱申领", level1, "2");
        addNavMenu(prefix, "申领配发", level1, "2");
        addNavMenu(prefix, "出港板箱", level1, "2");
        addNavMenu(prefix, "损坏登记", level1, "2");
        addNavMenu(prefix, "借出返还", level1, "2");
        addNavMenu(prefix, "报废登记", level1, "2");
        addNavMenu(prefix, "存场查询", level1, "2");
        addNavMenu(prefix, "借出查询", level1, "2");
        addNavMenu(prefix, "历史查询", level1, "2");
        addNavMenu(prefix, "进出港量统计", level1, "2");
        addNavMenu(prefix, "航班ULD清单", level1, "2");
        addNavMenu(prefix, "配发统计", level1, "2");
        addNavMenu(prefix, "存场统计", level1, "2");

        level1 = addNavMenu("nav_", "收费与账单", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "发票查询", level1, "2");
        addNavMenu(prefix, "国内进港授权计费", level1, "2");
        addNavMenu(prefix, "国内出港授权计费", level1, "2");
        addNavMenu(prefix, "国内进港费用定义", level1, "2");
        addNavMenu(prefix, "国内出港费用定义", level1, "2");
        addNavMenu(prefix, "国内进港邮航包机收费", level1, "2");
        addNavMenu(prefix, "国内出港邮航包机收费", level1, "2");
        addNavMenu(prefix, "国际进港邮航包机收费", level1, "2");
        addNavMenu(prefix, "国际出港邮航包机收费", level1, "2");
        addNavMenu(prefix, "国内进港临时收费", level1, "2");
        addNavMenu(prefix, "国内出港临时收费", level1, "2");
        addNavMenu(prefix, "批量结算", level1, "2");
        addNavMenu(prefix, "包机批量结算", level1, "2");

        level1 = addNavMenu("nav_", "KPI管理", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "KPI定义", level1, "2");
        addNavMenu(prefix, "航班状态", level1, "2");

        level1 = addNavMenu("nav_", "报文交换", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "航班报文计划", level1, "2");
        addNavMenu(prefix, "运单报文计划", level1, "2");
        addNavMenu(prefix, "FSU报文计划", level1, "2");
        addNavMenu(prefix, "FFM报文", level1, "2");
        addNavMenu(prefix, "FSU报文", level1, "2");
        addNavMenu(prefix, "UCM报文", level1, "2");
        addNavMenu(prefix, "SCM报文", level1, "2");
        addNavMenu(prefix, "自由报文", level1, "2");
        addNavMenu(prefix, "接收地址", level1, "2");
        addNavMenu(prefix, "报文查询", level1, "2");

        level1 = addNavMenu("nav_", "实时统计", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "设置报表", level1, "2");
        addNavMenu(prefix, "出港收运统计", level1, "2");
        addNavMenu(prefix, "AA", level1, "2");
        addNavMenu(prefix, "国际出口货量统计", level1, "2");
        addNavMenu(prefix, "美西北进港航班货量统计", level1, "2");
        addNavMenu(prefix, "国际货物（出口）运输销售日报", level1, "2");
        addNavMenu(prefix, "集装器巡场清单", level1, "2");

        level1 = addNavMenu("nav_", "公用工具", "root", "1");
        prefix = level1.split("_")[1] + "_";
        addNavMenu(prefix, "业务消息", level1, "2");
        addNavMenu(prefix, "个人选项", level1, "2");
        addNavMenu(prefix, "数据下载", level1, "2");
        addNavMenu(prefix, "报表配置", level1, "2");
    }

    private static String addNavMenu(String prefix, String displayName, String pid, String level) throws Exception {
        String pinyin = Cn2Spell.converterToFirstSpell(displayName);
        String id = prefix + pinyin;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        param.put("name", id);
        param.put("display_name", displayName);
        param.put("pid", pid);
        param.put("level", level);
        param.put("is_valid", "T");
        param.put("input_date", new Date());
        param.put("project_id", PROJECT_NAME);
        int sortNum = "1".equals(level) ? (level1SortNum += 10) : (level2SortNum += 10);
        param.put("sort_num", sortNum);
        template.save(param, "mu_nav_menu");

        return id;
    }

    public static void main(String[] args) throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            initProject(template);
            initNavMenu(template);
            template.commit();
        } finally {
            template.close();
        }
    }
}
