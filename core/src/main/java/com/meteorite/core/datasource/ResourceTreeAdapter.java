package com.meteorite.core.datasource;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.ui.model.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ResourceTreeAdapter extends BaseTreeNode {
    private VirtualResource resource;
    private List<ITreeNode> children;
    private ResourceTreeAdapter parent;

    public ResourceTreeAdapter(VirtualResource resource) throws Exception {
        this.resource = resource;

        children = new ArrayList<ITreeNode>();
        for (VirtualResource rs : resource.getChildren()) {
            children.add(new ResourceTreeAdapter(rs));
        }
    }

    @Override
    public View getView() {
        return resource.getView();
    }

    @Override
    public String getPresentableText() {
        return resource.getDisplayName();
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public String getId() {
        return resource.getPath();
    }

    @Override
    public String getPid() {
        return null;
    }

    @Override
    public ITreeNode getParent() {
        return parent;
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
        return resource.getName();
    }

    @Override
    public String getDisplayName() {
        return resource.getDisplayName();
    }

    @Override
    public int getSortNum() {
        return 0;
    }

    @Override
    public String getIcon() {
        return resource.getResourceType().getIcon();
    }

    @Override
    public String toString() {
        return resource.getDisplayName();
    }

    public VirtualResource getResource() {
        return resource;
    }
}
