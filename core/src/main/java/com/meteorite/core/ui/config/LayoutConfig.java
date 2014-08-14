package com.meteorite.core.ui.config;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 布局配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement(name = "Layout")
@XmlType(propOrder = {"id", "name", "displayName", "sortNum", "desc", "actionConfigs", "properties", "children"})
public class LayoutConfig implements ILayoutConfig {
    /** 布局ID */
    private String id;
    /** 布局名称 */
    private String name;
    /** 布局显示名 */
    private String displayName;
    /** 描述 */
    private String desc;
    /** 是否设计视图 */
    private boolean isDesign;
    /** 排序号*/
    private int sortNum;

    private LayoutConfig parent;
    private List<ILayoutConfig> children;
    private List<IActionConfig> actionConfigs;
    private List<ILayoutProperty> properties;

    public LayoutConfig() {}

    public LayoutConfig(String name, String displayName, String desc, LayoutConfig parent) {
        this.name = name;
        this.displayName = displayName;
        this.desc = desc;
        this.parent = parent;
        // 将子节点添加到父节点中
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    public void setId(String id) {
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

    public void setParent(LayoutConfig parent) {
        this.parent = parent;
    }

    public void setProperties(List<ILayoutProperty> properties) {
        this.properties = properties;
    }

    @Override
    @XmlAttribute
    public String getId() {
        return id;
    }

    @Override
    @XmlAttribute
    public String getName() {
        return name;
    }

    @Override
    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    @Override
    @XmlAttribute
    public String getDesc() {
        return desc;
    }

    @Override
    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    @Override
    @XmlTransient
    public LayoutConfig getParent() {
        return parent;
    }

    @Override
    @XmlElement(type = LayoutConfig.class, name = "Layout")
    @XmlElementWrapper(name = "children")
    public List<ILayoutConfig> getChildren() {
        if (children == null) {
            children = new ArrayList<ILayoutConfig>();
        }
        return children;
    }

    @Override
    @XmlElement(type = ActionConfig.class, name = "Action")
    @XmlElementWrapper(name = "Actions")
    public List<IActionConfig> getActionConfigs() {
        if (actionConfigs == null) {
            actionConfigs = new ArrayList<IActionConfig>();
        }
        return actionConfigs;
    }

    @Override
    @XmlElement(type = LayoutProperty.class, name = "Property")
    @XmlElementWrapper(name = "Properties")
    public List<ILayoutProperty> getProperties() {
        if (properties == null) {
            properties = new ArrayList<ILayoutProperty>();
        }
        return properties;
    }

    @Override
    public ILayoutProperty getProperty(String propName) {
        for (ILayoutProperty property : properties) {
            if (property.getName().equals(propName)) {
                return property;
            }
        }

        throw new RuntimeException(String.format("布局【%s】没有找到属性【%s】", getName(), propName));
    }

    @Override
    public String getPropStringValue(String propName) {
        ILayoutProperty property = getProperty(propName);
        if (property != null) {
            return UString.isEmpty(property.getValue()) ? property.getDefaultValue() : property.getValue();
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

    @Override
    public void setPropValue(String propName, String value) {
        System.out.println("Set Property: " + propName + " > " + value);
        getProperty(propName).setValue(value);
    }

    @Override
    public ILayoutConfig clone() {
        try {
            LayoutConfig config = (LayoutConfig) super.clone();
            // Clone Children
            List<ILayoutConfig> childrenList = new ArrayList<ILayoutConfig>();
            for (ILayoutConfig layoutConfig : children) {
                childrenList.add(layoutConfig.clone());
            }
            config.setChildren(childrenList);

            // Clone Property
            List<ILayoutProperty> propertyList = new ArrayList<ILayoutProperty>();
            for (ILayoutProperty property : properties) {
                propertyList.add(property.clone());
            }
            config.setProperties(propertyList);

            // Clone Action
            List<IActionConfig> actionConfigList = new ArrayList<IActionConfig>();
            for (IActionConfig action : actionConfigs) {
                actionConfigList.add(action.clone());
            }
            config.setActionConfigs(actionConfigList);

            return config;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
