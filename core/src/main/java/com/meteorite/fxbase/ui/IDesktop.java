package com.meteorite.fxbase.ui;

import javafx.scene.Parent;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IDesktop {
    /**
     * 初始化UI控件
     *
     * @since 1.0.0
     */
    void initUI();

    /**
     * 获得桌面UI控件
     *
     * @return 返回桌面
     */
    Parent getDesktop();
}
