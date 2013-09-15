package com.meteorite.core.model.impl;

import com.meteorite.core.model.ITreeModel;
import com.meteorite.core.model.ITreeNode;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseTreeModel implements ITreeModel {
    private String name;
    private ITreeNode root;

    public void setName(String name) {
        this.name = name;
    }

    public void setRoot(ITreeNode root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ITreeNode getRoot() {
        return root;
    }
}
