package com.meteorite.fxbase.ui;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * 表单字段接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IFormField {
    /**
     * 设置Label
     *
     * @param label Label
     */
    void setLabel(Label label);

    /**
     * 设置显示风格
     *
     * @param displayStyle 显示风格
     */
    void setDisplayStyle(DisplayStyle displayStyle);

    /**
     * 获得Label
     *
     * @return 返回Label
     */
    Label getLabel();

    /**
     * 获得Node
     *
     * @return 返回Node
     */
    Node getNode();

    /**
     * 设置值
     *
     * @param values 值
     */
    void setValue(String... values);

    /**
     * 获得值
     *
     * @return 返回值
     */
    String getValue();

    /**
     * 获得值数组
     *
     * @return 返回值数组
     */
    String[] getValues();

    /**
     * Field Text属性，用于绑定Control
     *
     * @return 返回Field Text属性
     */
    StringProperty textProperty();

    /**
     * Field Width属性
     *
     * @return 返回Field Width属性
     */
    DoubleProperty widthProperty();

    /**
     * Field Height属性
     *
     * @return 返回Field Height属性
     */
    DoubleProperty heightProperty();

    /**
     * 获得表单字段配置信息
     *
     * @return 返回表单字段配置信息
     */
    FormFieldConfig getFormFieldConfig();

    /**
     * 注册布局事件
     */
    void registLayoutEvent();
}
