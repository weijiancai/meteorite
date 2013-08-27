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

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
