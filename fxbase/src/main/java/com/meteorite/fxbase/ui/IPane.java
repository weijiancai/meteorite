package com.meteorite.fxbase.ui;

/**
 * 面板接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IPane {
    /**
     * 注册布局事件
     */
    void registLayoutEvent();

    /**
     * 是否显示Top
     *
     * @param isShow 是否显示
     */
    void setShowTop(boolean isShow);
}
