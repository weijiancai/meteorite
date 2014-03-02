package com.meteorite.core;

import java.util.List;

/**
 * 树形接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ITree {
    /**
     * 获得树形节点ID
     *
     * @return 返回树形节点ID
     */
    String getId();

    /**
     * 获得树形节点显示名
     *
     * @return 返回树形节点显示名
     */
    String getDisplayName();

    /**
     * 获得树形节点下一级子节点
     *
     * @return 返回树形节点的下一级子节点
     */
    List<?> getChildren();
}
