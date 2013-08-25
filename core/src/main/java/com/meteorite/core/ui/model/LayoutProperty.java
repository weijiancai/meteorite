package com.meteorite.core.ui.model;

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
@XmlRootElement
@XmlType(propOrder = {"name", "cname", "defaultValue", "sortNum"})
public class LayoutProperty {
    /** 属性名称 */
    private String name;
    /** 属性中文名 */
    private String cname;
    /** 默认值 */
    private String defaultValue;
    /** 属性值 */
    private String value;
    /** 排序号*/
    private int sortNum;

    private Layout layout;

    public LayoutProperty() {}

    public LayoutProperty(String name, String cname, String defaultValue, int sortNum, Layout layout) {
        this.name = name;
        this.cname = cname;
        this.defaultValue = defaultValue;
        this.sortNum = sortNum;
        this.layout = layout;
    }

    public LayoutProperty(String name, String cname, String defaultValue, Layout layout) {
        this.name = name;
        this.cname = cname;
        this.defaultValue = defaultValue;
        this.layout = layout;
        // 将此属性添加到布局中
        layout.getProperties().add(this);
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @XmlAttribute
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlTransient
    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
