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

    String getPresentableText();
}
