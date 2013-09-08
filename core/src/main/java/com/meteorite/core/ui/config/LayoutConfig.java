package com.meteorite.core.ui.config;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import java.util.List;

/**
 * 布局配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutConfig implements ILayoutConfig {
    /** 布局ID */
    private int id;
    /** 布局名称 */
    private String name;
    /** 布局显示名 */
    private String displayName;
    /** 描述 */
    private String desc;
    /** 排序号*/
    private int sortNum;

    private ILayoutConfig parent;
    private List<ILayoutConfig> children;
    private List<IActionConfig> actionConfigs;
    private List<ILayoutProperty> properties;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setActionConfigs(List<IActionConfig> actionConfigs) {
        this.actionConfigs = actionConfigs;
    }

    public void setChildren(List<ILayoutConfig> children) {
        this.children = children;
    }

    public void setParent(ILayoutConfig parent) {
        this.parent = parent;
    }

    public void setProperties(List<ILayoutProperty> properties) {
        this.properties = properties;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public ILayoutConfig getParent() {
        return parent;
    }

    @Override
    public List<ILayoutConfig> getChildren() {
        return children;
    }

    @Override
    public List<IActionConfig> getActionConfigs() {
        return actionConfigs;
    }

    @Override
    public List<ILayoutProperty> getProperties() {
        return properties;
    }

    @Override
    public ILayoutProperty getProperty(String propName) {
        for (ILayoutProperty property : properties) {
            if (property.getName().equals(propName)) {
                return property;
            }
        }

        return null;
    }

    @Override
    public String getPropStringValue(String propName) {
        ILayoutProperty property = getProperty(propName);
        if (property != null) {
            return property.getValue();
        }

        return null;
    }

    @Override
    public int getPropIntValue(String propName) {
        return UNumber.toInt(getPropStringValue(propName));
    }

    @Override
    public boolean getPropBooleanValue(String propName) {
        return UString.toBoolean(getPropStringValue(propName));
    }

    @Override
    public MetaDataType getPropDataType(String propName) {
//        return getProperty(propName).getDataType();
        return MetaDataType.getDataType(getPropStringValue(propName));
    }
}
