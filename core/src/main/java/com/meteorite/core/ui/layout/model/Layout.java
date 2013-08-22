package com.meteorite.core.ui.layout.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 布局信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "pid", "name", "cname", "sortNum", "desc", "properties", "children"})
public class Layout {
    /** 布局ID*/
    private int id;
    /** 布局父ID*/
    private int pid;
    /** 布局名称 */
    private String name;
    /** 布局中文名 */
    private String cname;
    /** 描述 */
    private String desc;
    /** 排序号*/
    private int sortNum;

    private List<LayoutProperty> properties;
    private List<Layout> children;
    private Layout parent;

    public Layout() {}

    public Layout(int id, int pid, String name, String cname, String desc, int sortNum) {
        this.id = id;
        this.pid = pid;
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
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
}
