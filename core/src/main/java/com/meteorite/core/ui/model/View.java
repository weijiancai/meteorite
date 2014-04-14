package com.meteorite.core.ui.model;

import java.util.ArrayList;
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

    private List<ViewLayout> layoutList;
    private List<ViewConfig> configs;
    private List<ViewProperty> viewProperties;

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

    public List<ViewLayout> getLayoutList() {
        if (layoutList == null) {
            layoutList = new ArrayList<>();
        }
        return layoutList;
    }

    public void setLayoutList(List<ViewLayout> layoutList) {
        this.layoutList = layoutList;
    }

    public List<ViewConfig> getConfigs() {
        if (configs == null) {
            configs = new ArrayList<>();
        }
        return configs;
    }

    public void setConfigs(List<ViewConfig> configs) {
        this.configs = configs;
    }

    public void addConfig(ViewConfig config) {
        getConfigs().add(config);
    }

    public ViewLayout getTableLayout() {
        for (ViewLayout layout : layoutList) {
            if (layout.getLayout().getName().equals("TABLE")) {
                return layout;
            }
        }

        return null;
    }

    public ViewLayout getFormLayout() {
        for (ViewLayout layout : layoutList) {
            if (layout.getLayout().getName().equals("FORM")) {
                return layout;
            }
        }

        return null;
    }

    public List<ViewProperty> getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(List<ViewProperty> viewProperties) {
        this.viewProperties = viewProperties;
    }
}
