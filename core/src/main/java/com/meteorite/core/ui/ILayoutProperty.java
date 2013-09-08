package com.meteorite.core.ui;

import com.meteorite.core.meta.MetaDataType;

/**
 * 布局属性
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface ILayoutProperty {
    /**
     * 获得属性Id
     *
     * @return 返回属性Id
     */
    int getId();

    /**
     * 获得属性名称
     *
     * @return 返回属性名称
     */
    String getName();

    /**
     * 获得属性显示名称
     *
     * @return 返回属性显示名称
     */
    String getDisplayName();

    /**
     * 获得属性默认值
     *
     * @return 返回属性默认值
     */
    String getDefaultValue();

    /**
     * 获得属性值
     *
     * @return 返回属性值
     */
    String getValue();

    /**
     * 获得数据类型
     *
     * @return 返回数据类型
     */
    MetaDataType getDataType();

    /**
     * 获得属性排序号
     *
     * @return 返回属性排序号
     */
    int getSortNum();
}
