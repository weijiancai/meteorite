package com.meteorite.fxbase.ui;

import javafx.scene.control.Label;

/**
 * Label for node接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ILabelFor {
    /**
     * 设置Label
     *
     * @param label Label
     */
    void setLabel(Label label);

    /**
     * 获得Label
     *
     * @return 返回Label
     */
    Label getLabel();
}
