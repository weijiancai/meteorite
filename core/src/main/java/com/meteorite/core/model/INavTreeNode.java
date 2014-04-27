package com.meteorite.core.model;

import com.meteorite.core.ui.model.View;

/**
 * 导航树节点
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface INavTreeNode extends ITreeNode {
    View getView();

    /**
     * 获得导航树节点的图标路径
     *
     * @return 返回导航树节点的图标路径
     * @since 1.0.0
     */
    String getIcon();

    String getPresentableText();
}
