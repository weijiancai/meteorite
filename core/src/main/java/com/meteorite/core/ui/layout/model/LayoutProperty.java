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
    /** 属性父ID*/
    private int pid;
    /** 属性类型 */
    private String prop_type;
    /** 属性名称 */
    private String name;
    /** 属性中文名 */
    private String cname;
    /** 属性值 */
    private String value;
    /** 默认值 */
    private String defaultValue;
    /** 排序号*/
    private int sortNum;

    public LayoutProperty(String name, String cname, String defaultValue, int sortNum) {
        this.name = name;
        this.cname = cname;
        this.defaultValue = defaultValue;
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

    public String getProp_type() {
        return prop_type;
    }

    public void setProp_type(String prop_type) {
        this.prop_type = prop_type;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
