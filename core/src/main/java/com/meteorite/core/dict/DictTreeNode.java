package com.meteorite.core.dict;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictTreeNode extends BaseTreeNode {
    private DictCategory category;

    public DictTreeNode(DictCategory category) {
        this.category = category;
        List<ITreeNode> children = new ArrayList<ITreeNode>();
        for (DictCategory cat : category.getChildren()) {
            DictTreeNode node = new DictTreeNode(cat);
            node.setParent(this);
            children.add(node);
        }
        setChildren(children);
    }

    @Override
    public String getId() {
        return category.getId();
    }

    @Override
    public String getPid() {
        return category.getPid();
    }

    @Override
    public String getName() {
        return category.getId();
    }

    @Override
    public String getDisplayName() {
        return category.getName();
    }

    @Override
    public int getSortNum() {
        return category.getSortNum();
    }

    @Override
    public boolean isVirtual() {
        return "ROOT".equals(getId()) || "System_Category".equals(getId());
    }

    @Override
    public String toString() {
        return category.getName();
    }
}
