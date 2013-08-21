package com.meteorite.core.ui.layout.model;

import java.util.List;

/**
 * 布局信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
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

    public Layout(int id, int pid, String name, String cname, String desc, int sortNum) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.cname = cname;
        this.desc = desc;
        this.sortNum = sortNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public List<LayoutProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<LayoutProperty> properties) {
        this.properties = properties;
    }

    public List<Layout> getChildren() {
        return children;
    }

    public void setChildren(List<Layout> children) {
        this.children = children;
    }
}
