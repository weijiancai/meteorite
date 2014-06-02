package com.meteorite.core.model.impl;

import com.meteorite.core.model.ITreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class BaseTreeNode implements ITreeNode {
    private String id;
    private String pid;
    private String name;
    private String displayName;
    private int sortNum;

    private ITreeNode parent;
    private List<ITreeNode> children;

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setParent(ITreeNode parent) {
        this.parent = parent;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    @Override
    public List<ITreeNode> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
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

    @Override
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public void addChild(ITreeNode node) {
        node.setParent(this);
        getChildren().add(node);
    }
}
