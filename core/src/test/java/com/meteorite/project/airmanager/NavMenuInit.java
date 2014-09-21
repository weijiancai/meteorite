package com.meteorite.project.airmanager;

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

        NavMenu level1 = addNavMenu(root, "安全管理");
        NavMenu level2;
        addNavMenu(level1, "系统岗位");
        addNavMenu(level1, "组织机构");
        addNavMenu(level1, "系统用户");
        addNavMenu(level1, "登陆信息");
        addNavMenu(level1, "消息组配置");

        level1 = addNavMenu(root, "基础数据");
        addNavMenu(level1, "异常代码");
        addNavMenu(level1, "代理");
        addNavMenu(level1, "物品编码");
        addNavMenu(level1, "仓库位置");
        addNavMenu(level1, "ULD信息");

        level1 = addNavMenu(root, "系统管理");
        addNavMenu(level1, "用户管理");
        addNavMenu(level1, "航班管理");
        addNavMenu(level1, "场站管理");
        addNavMenu(level1, "不正常管理");
        addNavMenu(level1, "城市代码管理");

        level1 = addNavMenu(root, "国际出港");
        level2 = addNavMenu(level1, "出入仓管理");
        addNavMenu(level2, "入库调度");
        addNavMenu(level2, "收费处理");
        addNavMenu(level2, "退仓处理");
        addNavMenu(level2, "按航班收运入仓");
        addNavMenu(level2, "收运入仓");
        addNavMenu(level2, "运单收运入仓");
        level2 = addNavMenu(level1, "文件操作");
        addNavMenu(level2, "简易制单");
        addNavMenu(level2, "国际代单");
        addNavMenu(level2, "变更申请");
        addNavMenu(level2, "国际正式制单");
        addNavMenu(level2, "运单文件");
        addNavMenu(level2, "板箱申领");
        addNavMenu(level2, "拉货补运");
        addNavMenu(level2, "冷藏登记");
        addNavMenu(level2, "中转接收");
        addNavMenu(level2, "载量计划");
        addNavMenu(level2, "代理人差错查询");
        addNavMenu(level2, "航班文件");
        addNavMenu(level2, "海关报文管理");
        addNavMenu(level2, "中转换单");
        addNavMenu(level2, "分单制表");
        addNavMenu(level2, "分单拼单");
        addNavMenu(level2, "AMS舱单");
        addNavMenu(level2, "海关账册");

        level1 = addNavMenu(root, "国际进港");
        level2 = addNavMenu(level1, "文件操作");
        addNavMenu(level2, "航班文件");
        addNavMenu(level2, "分单录入理货");
        addNavMenu(level2, "中转转港");
        addNavMenu(level2, "联程货物统计");
        addNavMenu(level2, "海关报文管理");
        addNavMenu(level2, "海关账册");
        addNavMenu(level2, "进港运单高级管理");

        level2 = addNavMenu(level1, "货物操作");
        addNavMenu(level2, "清库查询");

        level2 = addNavMenu(level1, "提单办单");
        addNavMenu(level2, "提货通知");
        addNavMenu(level2, "提单");
        addNavMenu(level2, "提货办单");
        addNavMenu(level2, "介绍信");

        level2 = addNavMenu(level1, "提货出库");
        addNavMenu(level2, "提货出库");

        level2 = addNavMenu(level1, "相关操作");
        addNavMenu(level2, "运单状态");
        addNavMenu(level2, "运单日志");
        addNavMenu(level2, "不正常");
        addNavMenu(level2, "航班查询");
        addNavMenu(level2, "仓库管理");

        level1 = addNavMenu(root, "国内出港");
        level2 = addNavMenu(level1, "出入仓管理");
        addNavMenu(level2, "收费处理");
        addNavMenu(level2, "退仓处理");
        addNavMenu(level2, "收运入仓");
        addNavMenu(level2, "运单收运入仓");

        level2 = addNavMenu(level1, "文件操作");
        addNavMenu(level2, "简易制单");
        addNavMenu(level2, "国内正式制单");
        addNavMenu(level2, "拉货补运");
        addNavMenu(level2, "中转接收");
        addNavMenu(level2, "始发站换单");
        addNavMenu(level2, "出港运单高级管理");

        level2 = addNavMenu(level1, "货物操作");
        addNavMenu(level2, "板箱复磅");
        addNavMenu(level2, "地勤交接取消");
        addNavMenu(level2, "航班预配操作");
        addNavMenu(level2, "航班配载");
        addNavMenu(level2, "仓库管理");

        level2 = addNavMenu(level1, "相关操作");
        addNavMenu(level2, "不正常");
        addNavMenu(level2, "运单状态");
        addNavMenu(level2, "运单日志");
        addNavMenu(level2, "拉货查询");

        level1 = addNavMenu(root, "国内进港");
        level2 = addNavMenu(level1, "文件操作");
        addNavMenu(level2, "航班文件");
        addNavMenu(level2, "中转转港");
        addNavMenu(level2, "冷藏登记");
        addNavMenu(level2, "联程货物统计");
        addNavMenu(level2, "进港运单高级管理");
        addNavMenu(level2, "中转换单");
        addNavMenu(level2, "海关账册");

        level2 = addNavMenu(level1, "货物操作");
        addNavMenu(level2, "清库查询");

        level2 = addNavMenu(level1, "提单办单");
        addNavMenu(level2, "提货通知");
        addNavMenu(level2, "提货办单");
        addNavMenu(level2, "介绍信");
        addNavMenu(level2, "不正常交付记录");

        level2 = addNavMenu(level1, "提货出库");
        addNavMenu(level2, "提货出库");

        level2 = addNavMenu(level1, "相关操作");
        addNavMenu(level2, "运单状态");
        addNavMenu(level2, "运单日志");
        addNavMenu(level2, "不正常");
        addNavMenu(level2, "仓库管理");
        addNavMenu(level2, "运单归档查询");
        addNavMenu(level2, "航班查询");
        addNavMenu(level2, "卸机比对");
        addNavMenu(level2, "航班状态");
        addNavMenu(level2, "计费业务数据维护");

        level1 = addNavMenu(root, "集控器材");
        level2 = addNavMenu(level1, "集控操作");
        addNavMenu(level2, "机坪清点");
        addNavMenu(level2, "空箱返回");
        addNavMenu(level2, "备品出入库");
        addNavMenu(level2, "板箱申领");
        addNavMenu(level2, "申领配发");
        addNavMenu(level2, "出港板箱");
        addNavMenu(level2, "损坏登记");
        addNavMenu(level2, "借出返还");
        addNavMenu(level2, "报废登记");

        level2 = addNavMenu(level1, "查询统计");
        addNavMenu(level2, "存场查询");
        addNavMenu(level2, "借出查询");
        addNavMenu(level2, "历史查询");
        addNavMenu(level2, "进出港量统计");
        addNavMenu(level2, "航班ULD清单");
        addNavMenu(level2, "配发统计");
        addNavMenu(level2, "存场统计");

        level1 = addNavMenu(root, "收费与账单");
        level2 = addNavMenu(level1, "结算");
        addNavMenu(level2, "发票查询");

        level2 = addNavMenu(level1, "授权计费");
        addNavMenu(level2, "国内进港授权计费");
        addNavMenu(level2, "国内出港授权计费");
        addNavMenu(level2, "国际出港授权计费");
        addNavMenu(level2, "国际进港授权计费");

        level2 = addNavMenu(level1, "费用定义");
        addNavMenu(level2, "国内进港费用定义");
        addNavMenu(level2, "国内出港费用定义");

        level2 = addNavMenu(level1, "邮航包机收费");
        addNavMenu(level2, "国内进港邮航包机收费");
        addNavMenu(level2, "国内出港邮航包机收费");
        addNavMenu(level2, "国际进港邮航包机收费");
        addNavMenu(level2, "国际出港邮航包机收费");

        level2 = addNavMenu(level1, "临时收费");
        addNavMenu(level2, "国内进港临时收费");
        addNavMenu(level2, "国内出港临时收费");

        level2 = addNavMenu(level1, "结算");
        addNavMenu(level2, "批量结算");
        addNavMenu(level2, "包机批量结算");

        level1 = addNavMenu(root, "KPI管理");
        addNavMenu(level1, "KPI定义");
        addNavMenu(level1, "航班状态");

        level1 = addNavMenu(root, "报文交换");
        level2 = addNavMenu(level1, "报文计划");
        addNavMenu(level2, "航班报文计划");
        addNavMenu(level2, "运单报文计划");
        addNavMenu(level2, "FSU报文计划");

        level2 = addNavMenu(level1, "报文发送");
        addNavMenu(level2, "FFM报文");
        addNavMenu(level2, "FSU报文");
        addNavMenu(level2, "UCM报文");
        addNavMenu(level2, "SCM报文");
        addNavMenu(level2, "自由报文");

        level2 = addNavMenu(level1, "其它");
        addNavMenu(level2, "接收地址");
        addNavMenu(level2, "报文查询");

        level1 = addNavMenu(root, "实时统计");
        addNavMenu(level1, "设置报表");

        level2 = addNavMenu(level1, "收运统计");
        addNavMenu(level2, "出港收运统计");

        level2 = addNavMenu(level1, "提货统计");
        addNavMenu(level2, "AA");

        level2 = addNavMenu(level1, "出港货量");
        addNavMenu(level2, "国际出口货量统计");

        level2 = addNavMenu(level1, "进港货量");
        addNavMenu(level2, "美西北进港航班货量统计");

        level2 = addNavMenu(level1, "销售日报");
        addNavMenu(level2, "国际货物（出口）运输销售日报");

        level2 = addNavMenu(level1, "集控统计");
        addNavMenu(level2, "集装器巡场清单");

        level1 = addNavMenu(root, "公用工具");
        addNavMenu(level1, "业务消息");
        addNavMenu(level1, "个人选项");
        addNavMenu(level1, "数据下载");
        addNavMenu(level1, "报表配置");
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
