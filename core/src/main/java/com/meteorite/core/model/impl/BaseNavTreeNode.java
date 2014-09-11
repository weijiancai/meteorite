package com.meteorite.core.model.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.ui.model.View;

/**
 * 导航树节点
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseNavTreeNode extends BaseTreeNode implements INavTreeNode {
    private View view;

    public BaseNavTreeNode() {}

    public BaseNavTreeNode(String displayName) {
        super(displayName);
    }

    @Override
    @JSONField(serialize = false)
    public View getView() {
        return view;
    }

    @Override
    public String getPresentableText() {
        return null;
    }

    public void setView(View view) {
        this.view = view;
    }
}
