package com.meteorite.core.ui;

import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.model.LayoutProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.util.UUIDUtil;

import java.sql.Connection;
import java.util.*;

/**
 * 视图管理器
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class ViewManager {
    private static Map<String, View> viewIdMap = new HashMap<>();
    private static Map<String, View> viewNameMap = new HashMap<>();

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        Connection conn = DBManager.getConnection(DBManager.getSysDataSource()).getConnection();
        JdbcTemplate template = new JdbcTemplate(conn);

        // 从数据库中加载视图
        if (sysInfo.isViewInit()) {

        } else {
            // 暂时不需要初始化
        }

        template.close();
    }

    public static View createFormView(Meta meta) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "Form");
        view.setDisplayName(meta.getDisplayName() + "表单");
        view.setDesc(meta.getDisplayName() + "视图");
        view.setLayout(LayoutManager.getLayoutByName("FORM"));
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewConfig> configList = new ArrayList<>();
        for (MetaField field : meta.getFields()) {
            for (LayoutProperty property : view.getLayout().getProperties()) {
                ViewConfig config = new ViewConfig();
                config.setId(UUIDUtil.getUUID());
                config.setField(field);
                config.setProperty(property);
                config.setView(view);
                config.setValue(property.getDefaultValue());

                configList.add(config);
            }
        }
        view.setConfigs(configList);

        return view;
    }
}
