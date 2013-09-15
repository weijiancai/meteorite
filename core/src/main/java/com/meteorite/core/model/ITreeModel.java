package com.meteorite.core.model;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public interface ITreeModel {
    /**
     * 模型名称
     *
     * @return 返回模型名称
     */
    String getName();

    /**
     * 获得树的根
     *
     * @return 返回树的根
     */
    ITreeNode getRoot();
}
