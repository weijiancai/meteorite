package com.meteorite.core.ui;

/**
 * 布局
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface ILayout {
    /**
     * 获取布局属性
     *
     * @return 返回布局属性
     */
    <P> P getProperty();

    <P> void setProperty(P property);

    /**
     * 进行布局，返回布局后的可视化对象
     *
     * @return 返回布局后的可视化对象
     */
    <T> T layout();
}
