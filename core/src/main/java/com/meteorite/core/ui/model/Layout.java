package com.meteorite.core.ui.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 布局信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement(name = "Layout")
@XmlType(propOrder = {"id", "name", "cname", "sortNum", "desc", "actions", "properties", "children"})
public class Layout implements Cloneable {
    /** 布局ID*/
    private int id;
    /** 布局名称 */
    private String name;
    /** 布局中文名 */
    private String cname;
    /** 描述 */
    private String desc;
    /** 排序号*/
    private int sortNum;

    private List<Action> actions;
    private List<LayoutProperty> properties;
    private List<Layout> children;
    private Layout parent;

    public Layout() {}

    public Layout(int id, String name, String cname, String desc, int sortNum) {
        this.id = id;
        this.name = name;
        this.cname = cname;
        this.desc = desc;
        this.sortNum = sortNum;
    }

    public Layout(String name, String cname, String desc, Layout parent) {
        this.name = name;
        this.cname = cname;
        this.desc = desc;
        this.parent = parent;
        // 将子节点添加到父节点中
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlElement(name = "Action")
    @XmlElementWrapper(name = "Actions")
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @XmlElement(name = "Property")
    @XmlElementWrapper(name = "Properties")
    public List<LayoutProperty> getProperties() {
        if (properties == null) {
            properties = new ArrayList<LayoutProperty>();
        }
        return properties;
    }

    public void setProperties(List<LayoutProperty> properties) {
        this.properties = properties;
    }

    @XmlElement(name = "Layout")
    public List<Layout> getChildren() {
        if (children == null) {
            children = new ArrayList<Layout>();
        }
        return children;
    }

    public void setChildren(List<Layout> children) {
        this.children = children;
    }

    @XmlTransient
    public Layout getParent() {
        return parent;
    }

    public void setParent(Layout parent) {
        this.parent = parent;
    }

    public LayoutProperty getProperty(String propName) {
        for (LayoutProperty property : getProperties()) {
            if (propName.equals(property.getName())) {
                return property;
            }
        }

        return null;
    }

    public void setPropertyValue(String propName, String value) {

    }
}
