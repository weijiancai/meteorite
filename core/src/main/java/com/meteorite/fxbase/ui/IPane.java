package com.meteorite.fxbase.ui;

import com.meteorite.fxbase.ui.event.FxLayoutEvent;

import java.util.Map;

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
     * 注册布局配置改变事件
     *
     * @param layoutEvent 布局事件
     */
    void registLayoutConfigChangeEvent(FxLayoutEvent layoutEvent);


    /**
     * 是否显示Top
     *
     * @param isShow 是否显示
     */
    void setShowTop(boolean isShow);

    /**
     * 获得值Map
     */
    Map<String, String> getValueMap();
}
