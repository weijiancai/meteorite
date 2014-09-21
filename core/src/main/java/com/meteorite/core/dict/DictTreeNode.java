package com.meteorite.core.dict;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.model.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictTreeNode implements ITreeNode {
    private DictCategory category;
    private List<ITreeNode> children;

    public DictTreeNode(DictCategory category) {
        this.category = category;
        children = new ArrayList<ITreeNode>();
        for (DictCategory cat : category.getChildren()) {
            children.add(new DictTreeNode(cat));
        }
    }

    @Override
    public String getId() {
        return category.getId();
    }

    @Override
    public String getPid() {
        return null;
    }

    @Override
    public ITreeNode getParent() {
        return null;
    }

    @Override
    public void setParent(ITreeNode parent) {

    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
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
        return 0;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public String getPresentableText() {
        return null;
    }

    @Override
    public String toString() {
        return category.getName();
    }
}
