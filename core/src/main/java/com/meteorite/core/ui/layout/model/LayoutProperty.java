package com.meteorite.core.ui.layout.model;

/**
 * 布局属性信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutProperty {
    /** 属性ID*/
    private int id;
    /** 属性名称 */
    private String name;
    /** 属性中文名 */
    private String cname;
    /** 默认值 */
    private String defaultValue;
    /** 排序号*/
    private int sortNum;

    private Layout layout;

    public LayoutProperty(int id, String name, String cname, String defaultValue, int sortNum, Layout layout) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
