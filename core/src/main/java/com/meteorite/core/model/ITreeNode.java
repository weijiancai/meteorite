package com.meteorite.core.model;

import com.meteorite.core.observer.EventData;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.ui.model.View;

import java.util.List;

/**
 * @author wei_jc
 * @since  1.0.0
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
     * 设置父节点
     *
     * @param parent 父节点
     */
    void setParent(ITreeNode parent);

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

    /**
     * 获得树节点的图标路径
     *
     * @return 返回树节点的图标路径
     * @since 1.0.0
     */
    String getIcon();

    View getView();

    String getPresentableText();

    /**
     * 获得此节点的所有数据
     *
     * @return 返回此节点的所有数据
     * @since 1.0.0
     */
//    DataMap getData();

    /**
     * 是否虚拟的树形节点，如果是虚拟的，则不会处理相关的事件，例如根据树形节点检索数据。
     *
     * @return 如果是虚拟节点，返回true，否则false。
     * @since 1.0.0
     */
    boolean isVirtual();

    /**
     * 获得此节点的PresentableText主题
     *
     * @return 返回此节点的PresentableText主题
     * @since 1.0.0
     */
    Subject<EventData> getPresentableTextSubject();
}
