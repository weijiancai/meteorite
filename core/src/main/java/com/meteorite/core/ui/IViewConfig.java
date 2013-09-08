package com.meteorite.core.ui;

/**
 * 用户界面（视图）配置
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IViewConfig {
    /**
     * 获得视图ID
     *
     * @return 返回视图Id
     */
    int getId();

    /**
     * 获得视图名称
     *
     * @return 返回视图名称
     */
    String name();

    /**
     * 获得视图显示名
     *
     * @return 返回视图显示名
     */
    String displayName();

    /**
     * 获得视图排序号
     *
     * @return 返回视图排序号
     */
    int getSortNum();

    /**
     * 获得布局管理器
     *
     * @return 返回布局管理器
     */
    ILayoutConfig getLayoutConfig();
}
