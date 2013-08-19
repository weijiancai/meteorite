package com.meteorite.core.ui;

import com.meteorite.core.ui.layout.LayoutProperty;
import com.meteorite.core.ui.layout.LayoutType;

import java.util.List;
import java.util.Map;

/**
 * 布局
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface ILayout<T> {
    /**
     * 设置布局属性
     *
     * @param properties 布局属性
     */
    void setProperties(Map<LayoutType, List<LayoutProperty>> properties);

    /**
     * 添加布局属性
     *
     * @param type 布局类型
     * @param property 布局属性
     */
    void addProperty(LayoutType type, LayoutProperty property);

    /**
     * 进行布局，返回布局后的可视化对象
     */
    T layout();
}
