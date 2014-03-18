package com.meteorite.core.ui.config;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ILayoutProperty;

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
@XmlType(propOrder = {"id", "name", "displayName", "defaultValue", "sortNum"})
public class LayoutProperty implements ILayoutProperty {
    /** 属性ID */
    private int id;
    /** 属性名称 */
    private String name;
    /** 属性显示名 */
    private String displayName;
    /** 默认值 */
    private String defaultValue;
    /** 属性值 */
    private String value;
    /** 数据类型 */
    private MetaDataType dataType;
    /** 数据字典ID */
    private String dictId;
    /** 排序号*/
    private int sortNum;

    private LayoutConfig layoutConfig;

    public LayoutProperty() {}

    public LayoutProperty(String name, String displayName, String value, MetaDataType dataType, String defaultValue) {
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
        this.name = name;
        this.value = value;
    }

    public LayoutProperty(String name, String displayName, String defaultValue, LayoutConfig layoutConfig) {
        this.name = name;
        this.displayName = displayName;
        this.defaultValue = defaultValue;
        this.layoutConfig = layoutConfig;
        // 将此属性添加到布局中
        layoutConfig.getProperties().add(this);
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    @XmlAttribute
    public int getId() {
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
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    @XmlAttribute
    public String getValue() {
        return value;
    }

    @Override
    @XmlAttribute
    public MetaDataType getDataType() {
        return dataType;
    }

    @Override
    @XmlAttribute
    public String getDictId() {
        return dictId;
    }

    @Override
    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public ILayoutProperty clone() {
        try {
            return (ILayoutProperty)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @XmlTransient
    public LayoutConfig getLayoutConfig() {
        return layoutConfig;
    }

    public void setLayoutConfig(LayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
    }
}
