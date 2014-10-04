package com.meteorite.core.project;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.model.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航菜单树形节点
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class NavMenuTreeNode implements ITreeNode {
    private NavMenu navMenu;
    private List<ITreeNode> children;

    public NavMenuTreeNode(NavMenu navMenu) {
        this.navMenu = navMenu;
        children = new ArrayList<ITreeNode>();
        for (NavMenu menu : navMenu.getChildren()) {
            children.add(new NavMenuTreeNode(menu));
        }
    }

    @Override
    public String getId() {
        return navMenu.getId();
    }

    @Override
    public String getPid() {
        return navMenu.getPid();
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
        return navMenu.getName();
    }

    @Override
    public String getDisplayName() {
        return navMenu.getDisplayName();
    }

    @Override
    public int getSortNum() {
        return navMenu.getSortNum();
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
    public boolean isVirtual() {
        return "root".equalsIgnoreCase(getId());
    }

    @Override
    public String toString() {
        return navMenu.getDisplayName();
    }
}
