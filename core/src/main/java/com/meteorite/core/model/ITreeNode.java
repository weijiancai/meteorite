package com.meteorite.core.model;

import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public interface ITreeNode {
    /**
     * 获得树形节点ID
     *
     * @return 返回树形节点ID
     */
    String getId();

    /**
     * 获得树形父节点ID
     *
     * @return 返回树形父节点ID
     */
    String getPid();

    /**
     * 获得父树形节点
     *
     * @return 返回父树形节点
     */
    ITreeNode getParent();

    /**
     * 获得孩子节点
     *
     * @return 返回孩子节点
     */
    List<ITreeNode> getChildren();

    /**
     * 获得名称
     *
     * @return 返回名称
     */
    String getName();

    /**
     * 获得显示名
     *
     * @return 返回显示名
     */
    String getDisplayName();

    /**
     * 获得排序号
     *
     * @return 返回排序号
     */
    int getSortNum();
}
