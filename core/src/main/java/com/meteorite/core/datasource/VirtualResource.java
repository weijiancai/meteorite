package com.meteorite.core.datasource;

/**
 * 虚拟资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class VirtualResource {

    public abstract String getName();

    public abstract String getPath();

    public abstract String getUrl();

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

    public abstract VirtualResource[] getChildren();

    public VirtualResource findChild(String name) {
        VirtualResource[] children = getChildren();
        if (children == null) return null;
        for (VirtualResource child : children) {
            if (child.nameEquals(name)) {
                return child;
            }
        }
        return null;
    }

    public abstract void delete(Object requestor);

    public boolean exists() {
        return isValid();
    }

    public abstract boolean isValid();

    protected boolean nameEquals(String name) {
        return getName().equals(name);
    }
}
