package com.meteorite.core.ui;

/**
 * 用户界面（视图）接口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IView<L extends ILayout> {
    void initUI();

    L getLayout();
}
