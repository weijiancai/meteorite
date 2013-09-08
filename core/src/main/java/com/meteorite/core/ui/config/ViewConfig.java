package com.meteorite.core.ui.config;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.IViewConfig;

/**
 * 视图配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ViewConfig implements IViewConfig {
    /** 视图ID*/
    private int id;
    /** 视图名称 */
    private String name;
    /** 显示名称 */
    private String displayName;
    /** 排序号 */
    private int sortNum;

    /** 布局 */
    private ILayoutConfig layoutConfig;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setLayoutConfig(ILayoutConfig layoutConfig) {
        this.layoutConfig = layoutConfig;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public ILayoutConfig getLayoutConfig() {
        return layoutConfig;
    }
}
