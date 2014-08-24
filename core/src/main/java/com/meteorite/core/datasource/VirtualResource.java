package com.meteorite.core.datasource;

import com.meteorite.core.ui.model.View;

import java.util.List;

/**
 * 虚拟资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class VirtualResource {
    /**
     * 获得资源的名称
     *
     * @return 返回资源的名称
     */
    public abstract String getName();

    public abstract String getPath();

    public abstract String getUrl();

    public abstract ResourceType getResourceType();

    /**
     * 获得资源的视图信息
     *
     * @return 返回资源视图
     */
    public abstract View getView();

    /**
     * 获得用来显示的名称，默认为资源名称
     *
     * @return 返回显示名称
     */
    public String getDisplayName() {
        return getName();
    }

    public String getExtension() {
        String name = getName();
        int index = name.lastIndexOf('.');
        if (index < 0) return null;
        return name.substring(index + 1);
    }

    public String getNameWithoutExtension() {
        String name = getName();
        int index = name.lastIndexOf('.');
        if (index < 0) return name;
        return name.substring(0, index);
    }

    public abstract VirtualResource getParent();

    public abstract List<VirtualResource> getChildren() throws Exception;

    public VirtualResource findChild(String name) throws Exception {
        List<VirtualResource> children = getChildren();
        if (children == null) return null;
        for (VirtualResource child : children) {
            if (child.nameEquals(name)) {
                return child;
            }
        }
        return null;
    }

    public abstract void delete();

    public boolean exists() {
        return isValid();
    }

    public abstract boolean isValid();

    protected boolean nameEquals(String name) {
        return getName().equals(name);
    }
}
