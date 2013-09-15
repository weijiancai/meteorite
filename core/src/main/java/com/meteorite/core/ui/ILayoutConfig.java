package com.meteorite.core.ui;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.config.LayoutConfig;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * 布局
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface ILayoutConfig extends ConfigConst, Cloneable {
    /**
     * 获得布局ID
     *
     * @return 返回布局ID
     */
    int getId();

    /**
     * 获得布局名称
     *
     * @return 返回布局名称
     */
    String getName();

    /**
     * 获得布局显示名称
     *
     * @return 返回布局显示名称
     */
    String getDisplayName();

    /**
     * 获得布局描述信息
     *
     * @return 返回布局描述信息
     */
    String getDesc();

    /**
     * 获得布局排序号
     *
     * @return 返回布局排序号
     */
    int getSortNum();

    /**
     * 获得上级布局
     *
     * @return 返回上级布局
     */
    ILayoutConfig getParent();

    /**
     * 设置下一级所有布局
     *
     * @param children 下一级所有布局
     */
    void setChildren(List<ILayoutConfig> children);

    /**
     * 获得下一级所有布局
     *
     * @return 返回下一级所有布局
     */
    List<ILayoutConfig> getChildren();

    /**
     * 获得布局的Actions
     *
     * @return 返回布局的Actions
     */
    List<IActionConfig> getActionConfigs();

    /**
     * 获得布局属性
     *
     * @return 返回布局属性
     */
    List<ILayoutProperty> getProperties();

    /**
     * 根据属性名，获得布局属性信息
     *
     * @param propName 属性名
     * @return 返回布局属性信息
     */
    ILayoutProperty getProperty(String propName);

    /**
     * 获得布局属性字符串值
     *
     * @param propName   属性名称
     * @return 返回布局属性字符串值
     */
    String getPropStringValue(String propName);

    /**
     * 获得布局属性整数值
     *
     * @param propName   属性名称
     * @return 返回布局属性整数值
     */
    int getPropIntValue(String propName);

    /**
     * 获得布局属性Boolean值
     *
     * @param propName   属性名称
     * @return 返回布局属性Boolean值
     */
    boolean getPropBooleanValue(String propName);

    /**
     * 获得布局属性数据类型
     *
     * @param propName 属性名称
     * @return 返回布局属性数据类型
     */
    MetaDataType getPropDataType(String propName);

    /**
     * 设置布局属性值
     *
     * @param propName 属性名
     * @param value    属性值
     */
    void setPropValue(String propName, String value);

    /**
     * Clone布局配置信息
     *
     * @return 返回Clone的布局配置信息
     */
    ILayoutConfig clone();
}
