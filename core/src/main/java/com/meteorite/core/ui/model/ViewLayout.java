package com.meteorite.core.ui.model;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.layout.PropertyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视图布局
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewLayout {
    /** 视图布局ID */
    private String id;

    private View view;
    private Layout layout;
    private Meta meta;
    private List<ViewConfig> configs;

    /** 元字段属性Map */
    private Map<String, Map<String, String>> fieldPropMap = new HashMap<String, Map<String, String>>();
    // 属性配置map
    private Map<String, String> configValueMap = new HashMap<String, String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ViewConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ViewConfig> configs) {
        this.configs = configs;
        for (ViewConfig config : configs) {
            if (config.getField() == null) {
                configValueMap.put(layout.getName() + "." + config.getProperty().getPropType().name() + "." + config.getProperty().getName(), config.getValue());
                continue;
            }

            String fieldId = config.getField().getId();
            Map<String, String> propMap = fieldPropMap.get(fieldId);
            if (propMap == null) {
                propMap = new HashMap<String, String>();
                fieldPropMap.put(fieldId, propMap);
            }
            propMap.put(config.getProperty().getName(), config.getValue());
        }
    }

    /**
     * 获得明细属性配置
     *
     * @return 返回明细属性配置
     */
    public List<ViewConfig> getIPConfigs() {
        List<ViewConfig> result = new ArrayList<ViewConfig>();
        for (ViewConfig config : configs) {
            if (config.getProperty().getPropType() == PropertyType.IP) {
                result.add(config);
            }
        }
        return result;
    }

    /**
     * 根据元字段Id获得此元字段的布局配置信息
     *
     * @param fieldId 元字段ID
     * @return 返回元字段配置
     */
    public Map<String, String> getMetaFieldConfig(String fieldId) {
        return fieldPropMap.get(fieldId);
    }

    /**
     * 根据key，获得属性配置值
     *
     * @param key key值（布局名称.属性类型.布局属性名称）
     * @return 返回属性配置值
     */
    public String getConfigValue(String key) {
        return configValueMap.get(key);
    }
}
