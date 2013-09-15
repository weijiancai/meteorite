package com.meteorite.core.model.impl;

import com.meteorite.core.model.ITreeNode;

import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseTreeNode implements ITreeNode {
    private String id;
    private String name;
    private String displayName;

    private BaseTreeNode parent;
    private List<ITreeNode> children;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setParent(BaseTreeNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
