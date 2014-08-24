package com.meteorite.core.datasource;

/**
 * 资源类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ResourceType {
    /**
     * 获得类型名称
     *
     * @return 返回类型名称
     */
    String getName();

    /**
     * 获得类型描述
     *
     * @return 返回类型描述信息
     */
    String getDescription();

    /**
     * 获得默认扩展名
     *
     * @return 返回默认扩展名
     */
    String getDefaultExtension();

    /**
     * 获得类型图标
     *
     * @return 返回类型图标
     */
    String getIcon();

    /**
     * 是否二进制资源
     *
     * @return 如果死二进制资源，返回true，否则返回false
     */
    boolean isBinary();
}
