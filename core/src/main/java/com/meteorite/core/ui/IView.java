package com.meteorite.core.ui;

/**
 * 用户界面（视图）接口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IView<T> {
    void initUI();

    ILayout getLayout();

    /**
     * 进行布局，返回布局后的可视化对象
     *
     * @return 返回布局后的可视化对象
     */
    T doLayout();
}
