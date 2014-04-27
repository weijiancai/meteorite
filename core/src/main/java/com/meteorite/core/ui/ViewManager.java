package com.meteorite.core.ui;

import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UString;
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
    private static Map<String, ViewProperty> propertyMap = new HashMap<>();

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        JdbcTemplate template = new JdbcTemplate(dataSource);

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
                    viewLayout.setConfigs(configList);
                    for (ViewConfig config : configList) {
                        configMap.put(config.getId(), config);
                        view.addConfig(config);
                    }
                }

                // 查询视图属性
                sql = "SELECT * FROM sys_view_prop WHERE view_id=?";
                List<ViewProperty> configList = template.query(sql, MetaRowMapperFactory.getViewProperty(view), view.getId());
                view.setViewProperties(configList);
                for (ViewProperty config : configList) {
                    propertyMap.put(config.getId(), config);
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
    public static void createViews(Meta meta, JdbcTemplate template) throws Exception {
        View formView = FormProperty.createFormView(meta, false);
        template.save(MetaPDBFactory.getView(formView));
        viewIdMap.put(formView.getId(), formView);
        viewNameMap.put(formView.getName(), formView);

        for (ViewProperty property : formView.getViewProperties()) {
            template.save(MetaPDBFactory.getViewProperty(property));
        }

        View tableView = TableProperty.createTableView(meta);
        template.save(MetaPDBFactory.getView(tableView));
        viewIdMap.put(tableView.getId(), tableView);
        viewNameMap.put(tableView.getName(), tableView);

        for (ViewProperty property : tableView.getViewProperties()) {
            template.save(MetaPDBFactory.getViewProperty(property));
        }

        View queryView = FormProperty.createFormView(meta, true);
        template.save(MetaPDBFactory.getView(queryView));
        viewIdMap.put(queryView.getId(), queryView);
        viewNameMap.put(queryView.getName(), queryView);

        for (ViewProperty property : queryView.getViewProperties()) {
            template.save(MetaPDBFactory.getViewProperty(property));
        }

        View crudView = CrudProperty.createCrudView(meta, formView, tableView, queryView);
        template.save(MetaPDBFactory.getView(crudView));
        viewIdMap.put(crudView.getId(), crudView);
        viewNameMap.put(crudView.getName(), crudView);

        for (ViewProperty property : crudView.getViewProperties()) {
            template.save(MetaPDBFactory.getViewProperty(property));
        }
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
                        configList.add(createViewConfig(viewLayout, property, null, null));
                        break;
                    }
                    case IP: {
                        for (MetaField field : meta.getFields()) {
                            configList.add(createViewConfig(viewLayout, property, field, null));
                        }
                    }
                }
            }
        }
        viewLayout.setConfigs(configList);

        return viewLayout;
    }

    public static ViewConfig createViewConfig(ViewLayout viewLayout, LayoutProperty property, MetaField field, String value) {
        ViewConfig config = new ViewConfig();
        config.setId(UUIDUtil.getUUID());
        if (field != null) {
            config.setField(field);
        }
        config.setProperty(property);
        config.setViewLayout(viewLayout);
        config.setValue(UString.isEmpty(value) ? property.getDefaultValue() : value);

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

    /**
     * 根据视图名称，获得视图信息
     *
     * @param viewName 视图名称
     * @return 返回视图信息
     * @since 1.0.0
     */
    public static View getViewByName(String viewName) {
        return viewNameMap.get(viewName);
    }
}
