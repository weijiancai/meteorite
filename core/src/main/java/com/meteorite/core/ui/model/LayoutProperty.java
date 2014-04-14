package com.meteorite.core.ui.model;

import com.meteorite.core.ui.layout.LayoutType;
import com.meteorite.core.ui.layout.PropertyType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * 布局属性信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement(name = "Property")
@XmlType(propOrder = {"id", "name", "displayName", "defaultValue", "propType", "desc", "sortNum"})
public class LayoutProperty {
    /** 属性ID */
    private String id;
    /**
     * 布局类型
     */
    private LayoutType layoutType;
    /** 属性名称 */
    private String name;
    /** 属性显示名 */
    private String displayName;
    /** 默认值 */
    private String defaultValue;
    /** 属性类型 */
    private PropertyType propType;
    /** 描述 */
    private String desc;
    /** 排序号*/
    private int sortNum;

    private Layout layout;

    public LayoutProperty() {}

    public LayoutProperty(String name, String displayName, String defaultValue, PropertyType propType, String desc) {
        this.name = name;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.propType = propType;
        this.desc = desc;
    }

    public LayoutProperty(String id, String displayName, LayoutType layoutType, String defaultValue, PropertyType propType, int sortNum) {
        this.id = id;
        this.name = id.split("\\.")[1];
        this.displayName = displayName;
        this.layoutType = layoutType;
        this.defaultValue = defaultValue;
        this.propType = propType;
        this.sortNum = sortNum;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    @XmlAttribute
    public String getDefaultValue() {
        return defaultValue;
    }

    @XmlAttribute
    public PropertyType getPropType() {
        return propType;
    }

    public void setPropType(PropertyType propType) {
        this.propType = propType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    @XmlTransient
    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
