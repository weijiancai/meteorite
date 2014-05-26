package com.meteorite.fxbase.ui.component;

/**
 * 组件接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IComponent {
    /**
     * 初始化之前
     */
    void initPrep();

    /**
     * 初始化UI组件
     */
    void initUI();

    /**
     * 初始化之后
     */
    void initAfter();

    /**
     * 初始化方法，调用initPrep, initUI, initAfter方法
     */
    void init();
}
