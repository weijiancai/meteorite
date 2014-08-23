package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.dict.EnumBoolean;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.ui.model.LayoutProperty;
import com.meteorite.core.util.UString;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ConfigConst.*;
/**
 * 布局管理
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutManager {
    private static Map<String, ILayoutConfig> cache = new HashMap<String, ILayoutConfig>();
    private static Map<String, Layout> layoutIdMap = new HashMap<String, Layout>();
    private static Map<String, Layout> layoutNameMap = new HashMap<String, Layout>();
    private static List<Layout> layoutList = new ArrayList<Layout>();
    private static Map<String, LayoutProperty> propMap = new HashMap<String, LayoutProperty>();
    private static Map<String, LayoutProperty> propNameMap = new HashMap<String, LayoutProperty>();
    private static Map<String, List<LayoutProperty>> layoutTypeMap = new HashMap<String, List<LayoutProperty>>();
    private static Layout root;

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        JdbcTemplate template = new JdbcTemplate();

        if (sysInfo.isLayoutInit()) {
            // 查询布局
            /*String sql = "SELECT * FROM sys_layout";
            List<Layout> layoutList = template.query(sql, MetaRowMapperFactory.getLayout());
            for (Layout layout : layoutList) {
                layoutIdMap.put(layout.getId(), layout);
                layoutNameMap.put(layout.getName(), layout);
                // 查询布局属性
                sql = "SELECT * FROM sys_layout_prop WHERE layout_id=?";
                List<LayoutProperty> propList = template.query(sql, MetaRowMapperFactory.getLayoutProperty(layout), layout.getId());
                for (LayoutProperty prop : propList) {
                    propMap.put(prop.getId(), prop);
                    propNameMap.put(layout.getName() + "." + prop.getPropType().name() + "." + prop.getName(), prop);
                }
                layout.setProperties(propList);
            }*/

            String sql = "SELECT * FROM sys_layout_prop";
            List<LayoutProperty> propList = template.query(sql, MetaRowMapperFactory.getLayoutProperty(null));
            for (LayoutProperty prop : propList) {
                propMap.put(prop.getId(), prop);
                List<LayoutProperty> list = layoutTypeMap.get(prop.getLayoutType().name());
                if (list == null) {
                    list = new ArrayList<LayoutProperty>();
                    layoutTypeMap.put(prop.getLayoutType().name(), list);
                }
                list.add(prop);
            }
        } else { // 初始化Layout
            /*if (root == null) {
                root = new Layout();
                root.load();
            }*/
            // 清空表
            template.clearTable("sys_view_config", "sys_layout", "sys_layout_prop");
//            iterator(root);

            // 保存Layout到数据库
            /*for (Layout layout : getLayoutList()) {
                template.save(MetaPDBFactory.getLayout(layout));
                for (LayoutProperty property : layout.getProperties()) {
                    Map<String, Object> map = template.queryForMap(String.format("select * from sys_layout where id='%s'", layout.getId()));
                    System.out.println(map);
                    property.setLayout(layout);
                    // 保存布局属性到数据库
                    template.save(MetaPDBFactory.getLayoutProperty(property));
                    propMap.put(property.getId(), property);
                    propNameMap.put(layout.getName() + "." + property.getPropType().name() + "." + property.getName(), property);
                }
            }*/
            // 保存布局属性到数据库
            for (LayoutProperty property : LayoutProperties.getAllProperties()) {
                template.save(MetaPDBFactory.getLayoutProperty(property));
                propMap.put(property.getId(), property);
            }

            sysInfo.setLayoutInit(true);
            sysInfo.store();
        }

        template.commit();
        template.close();
    }

    private static void iterator(Layout parent) {
        layoutIdMap.put(parent.getId(), parent);
        layoutNameMap.put(parent.getName(), parent);
        layoutList.add(parent);
        if (parent.getChildren() != null && parent.getChildren().size() > 0) {
            for (Layout child : parent.getChildren()) {
                child.setParent(parent);
                iterator(child);
            }
        }
    }

    public static ILayoutConfig getLayout(String name) {
        ILayoutConfig config = cache.get(name);
        if (config == null) {
            throw new RuntimeException(String.format("获取【%s】布局配置信息失败！", name));
        }
        return config.clone();
    }

    public static ILayoutConfig createLayout(Meta meta) {
        ILayoutConfig layout = getLayout(R.layout.FORM);
        return layout;
    }

    public static ILayoutConfig createFormLayout(Meta meta) {
        ILayoutConfig form = getLayout(R.layout.FORM);
        form.setPropValue(FORM_NAME, meta.getName() + "Form");
        form.setPropValue(FORM_DISPLAY_NAME, meta.getDisplayName() + "表单");

        List<ILayoutConfig> children = new ArrayList<ILayoutConfig>();
        for (MetaField field : meta.getFields()) {
            ILayoutConfig formField = getLayout(R.layout.FORM_FIELD);
            formField.setPropValue(FORM_FIELD_NAME, field.getName());
            formField.setPropValue(FORM_FIELD_DISPLAY_NAME, field.getDisplayName());
            formField.setPropValue(FORM_FIELD_SORT_NUM, field.getSortNum() + "");
            formField.setPropValue(FORM_FIELD_DATA_TYPE, field.getDataType() + "");
            if (MetaDataType.DATA_SOURCE == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.DATA_SOURCE.name());
                formField.setPropValue(FORM_FIELD_IS_SINGLE_LINE, "true");
            } else if (MetaDataType.PASSWORD == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.PASSWORD.name());
            } else if (MetaDataType.DICT == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.COMBO_BOX.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, field.getDictId());
            } else if (MetaDataType.BOOLEAN == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.BOOLEAN.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, EnumBoolean.class.getName());
            }
            children.add(formField);
        }
        form.setChildren(children);

        return form;
    }

    /**
     * 将布局配置信息转化为form表单配置
     *
     * @param layoutConfig 布局配置
     * @return 返回布form表单布局配置
     */
    public static ILayoutConfig createFormLayout(ILayoutConfig layoutConfig) {
        ILayoutConfig form = getLayout(R.layout.FORM);
        form.setPropValue(FORM_NAME, LAYOUT_FORM);
        form.setPropValue(FORM_DISPLAY_NAME, layoutConfig.getDisplayName() + "表单");

        List<ILayoutConfig> children = new ArrayList<ILayoutConfig>();
        for (ILayoutProperty field : layoutConfig.getProperties()) {
            if ("value".equals(field.getName())) {
                continue;
            }
            ILayoutConfig formField = getLayout(R.layout.FORM_FIELD);
            formField.setPropValue(FORM_FIELD_NAME, field.getName());
            formField.setPropValue(FORM_FIELD_DISPLAY_NAME, field.getDisplayName());
            formField.setPropValue(FORM_FIELD_SORT_NUM, field.getSortNum() + "");
            formField.setPropValue(FORM_FIELD_DATA_TYPE, field.getDataType() + "");
            if (MetaDataType.DATA_SOURCE == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.DATA_SOURCE.name());
                formField.setPropValue(FORM_FIELD_IS_SINGLE_LINE, "true");
            } else if (MetaDataType.PASSWORD == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.PASSWORD.name());
            } else if (MetaDataType.DICT == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.COMBO_BOX.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, field.getDictId());
            } else if (MetaDataType.BOOLEAN == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.BOOLEAN.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, EnumBoolean.class.getName());
            } else {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.TEXT_FIELD.name());
            }
            formField.setPropValue(FORM_FIELD_VALUE, UString.isEmpty(field.getValue()) ? field.getDefaultValue() : field.getValue());
            children.add(formField);
        }
        form.setChildren(children);

        return form;
    }

    public static List<Layout> getLayoutList() {
        return layoutList;
    }

    /**
     * 根据布局Id获得布局信息
     *
     * @param id 布局ID
     * @return 返回布局信息
     * @since 1.0.0
     */
    public static Layout getLayoutById(String id) {
        return layoutIdMap.get(id);
    }

    /**
     * 根据布局名称获得布局信息
     *
     * @param name 布局名称
     * @return 返回布局信息
     * @since 1.0.0
     */
    public static Layout getLayoutByName(String name) {
        return layoutNameMap.get(name);
    }

    /**
     * 根据布局属性ID，获得布局信息
     *
     * @param propId 布局属性ID
     * @return 返回布局信息
     * @since 1.0.0
     */
    public static LayoutProperty getLayoutPropById(String propId) {
        return propMap.get(propId);
    }

    /**
     * 根据布局属性名称，获得布局信息。
     *
     * @param propName 属性名称（布局名称.属性类型.布局属性名称）
     * @return 返回布局信息
     * @since 1.0.0
     */
    public static LayoutProperty getLayoutPropByName(String propName) {
        return propNameMap.get(propName);
    }
}
