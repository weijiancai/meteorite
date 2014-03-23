package com.meteorite.core.ui;

import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyType;
import com.meteorite.core.ui.model.*;
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
    private static Map<String, ViewLayout> viewLayoutMap = new HashMap<>();
    private static Map<String, ViewConfig> configMap = new HashMap<>();

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        Connection conn = DBManager.getConnection(DBManager.getSysDataSource()).getConnection();
        JdbcTemplate template = new JdbcTemplate(conn);

        // 从数据库中加载视图
        if (sysInfo.isViewInit()) {
            String sql = "SELECT * FROM sys_view";
            List<View> viewList = template.query(sql, MetaRowMapperFactory.getView());
            for (final View view : viewList) {
                viewIdMap.put(view.getId(), view);
                viewNameMap.put(view.getName(), view);
                // 查询视图布局
                sql = "SELECT * FROM sys_view_layout WHERE view_id=?";
                List<ViewLayout> viewLayoutList = template.query(sql, MetaRowMapperFactory.getViewLayout(view), view.getId());
                view.setLayoutList(viewLayoutList);
                for (ViewLayout viewLayout : viewLayoutList) {
                    viewLayoutMap.put(viewLayout.getId(), viewLayout);

                    // 查询视图配置
                    sql = "SELECT * FROM sys_view_config WHERE view_layout_id=?";
                    List<ViewConfig> configList = template.query(sql, MetaRowMapperFactory.getViewConfig(viewLayout), viewLayout.getId());
                    view.setConfigs(configList);
                    for (ViewConfig config : configList) {
                        configMap.put(config.getId(), config);
                        view.addConfig(config);
                    }
                }
            }
        } else {
            // 暂时不需要初始化
        }

        template.close();
    }

    /**
     * 根据元数据和布局创建视图
     *
     * @param meta 元数据
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View createView(Meta meta, JdbcTemplate template) throws Exception {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "View");
        view.setDisplayName(meta.getDisplayName());
        view.setDesc(meta.getDisplayName() + "视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        template.save(MetaPDBFactory.getView(view));

        ViewLayout formLayout = createViewLayout(meta, LayoutManager.getLayoutByName("FORM"));
        template.save(MetaPDBFactory.getViewLayout(formLayout));
        for (ViewConfig config : formLayout.getConfigs()) {
            template.save(MetaPDBFactory.getViewConfig(config));
        }

        ViewLayout tableLayout = createViewLayout(meta, LayoutManager.getLayoutByName("TABLE"));
        template.save(MetaPDBFactory.getViewLayout(tableLayout));
        for (ViewConfig config : tableLayout.getConfigs()) {
            template.save(MetaPDBFactory.getViewConfig(config));
        }

        return view;
    }

    public static ViewLayout createViewLayout(Meta meta, Layout layout) {
        ViewLayout viewLayout = new ViewLayout();
        viewLayout.setId(UUIDUtil.getUUID());
        viewLayout.setMeta(meta);
        viewLayout.setLayout(layout);

        List<ViewConfig> configList = new ArrayList<>();

        // 创建属性配置
        if (layout.getProperties() != null) {
            for (LayoutProperty property : layout.getProperties()) {
                switch (property.getPropType()) {
                    case MP: {
                        configList.add(createViewConfig(viewLayout, property, null));
                        break;
                    }
                    case IP: {
                        for (MetaField field : meta.getFields()) {
                            configList.add(createViewConfig(viewLayout, property, field));
                        }
                    }
                }
            }
        }

        return viewLayout;
    }

    private static ViewConfig createViewConfig(ViewLayout viewLayout, LayoutProperty property, MetaField field) {
        ViewConfig config = new ViewConfig();
        config.setId(UUIDUtil.getUUID());
        if (field != null) {
            config.setField(field);
        }
        config.setProperty(property);
        config.setViewLayout(viewLayout);
        config.setValue(property.getDefaultValue());

        return config;
    }

    /**
     * 根据视图ID，获得视图信息
     *
     * @param viewId 视图ID
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View getViewById(String viewId) {
        return viewIdMap.get(viewId);
    }
}
