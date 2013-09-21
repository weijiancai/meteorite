package com.meteorite.taobao;

import com.meteorite.core.model.ITreeNode;
import com.taobao.api.domain.ItemCat;

import java.util.ArrayList;
import java.util.List;

/**
 * 淘宝商品类目结构
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class TbItemCat extends ItemCat implements ITreeNode {
    private TbItemCat parent;
    private List<ITreeNode> children;

    public void setParent(TbItemCat parent) {
        this.parent = parent;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }

    public void addChild(ITreeNode child) {
        if (children == null) {
            children = new ArrayList<ITreeNode>();
        }
        children.add(child);
    }

    @Override
    public String getId() {
        return getCid() + "";
    }

    @Override
    public String getPid() {
        return getParentCid() + "";
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
    public String getDisplayName() {
        return getName();
    }

    @Override
    public int getSortNum() {
        return getSortOrder().intValue();
    }
}
