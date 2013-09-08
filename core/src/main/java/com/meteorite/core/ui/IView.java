package com.meteorite.core.ui;

/**
 * 用户界面（视图）接口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IView {
    /**
     * 初始化
     */
    void initUI();

    /**
     * 获得视图配置信息
     *
     * @return 返回视图配置信息
     */
    IViewConfig getViewConfig();
}
