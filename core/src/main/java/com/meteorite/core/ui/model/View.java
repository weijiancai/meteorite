package com.meteorite.core.ui.model;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class View {
    /** 视图ID*/
    private int id;
    /** 视图名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 排序号 */
    private int sortNum;

    private Layout layout;

    public View(int id, String name, Layout layout) {
        this.id = id;
        this.name = name;
        this.layout = layout;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
