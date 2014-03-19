package com.meteorite.core.ui.model;

import java.util.Date;
import java.util.List;

/**
 * 视图
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class View {
    /** 视图ID */
    private String id;
    /** 视图名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 描述 */
    private String desc;
    /** 是否有效 */
    private boolean isValid;
    /** 录入时间 */
    private Date inputDate;
    /** 排序号 */
    private int sortNum;

    private Layout layout;
    private List<ViewConfig> configs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
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

    public List<ViewConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ViewConfig> configs) {
        this.configs = configs;
    }
}
