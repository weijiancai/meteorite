package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.model.ITreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库对象List，用于树形结构中显示子节点数量的节点。例如tables
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBObjectList extends DBObjectImpl {
    public DBObjectList(String name, String icon, ArrayList<ITreeNode> children) {
        this.setName(name);
        this.setIcon(icon);
        this.setChildren(children);
        setObjectType(DBObjectType.NONE);
    }

    @Override
    public List<ITreeNode> getChildren() {
        List<ITreeNode> children =  super.getChildren();
        if (children == null) {
            children = new ArrayList<>();
        }

        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getName());
        List<ITreeNode> children = getChildren();
        if (children.size() > 0) {
            sb.append(" (").append(children.size()).append(")");
        }
        return sb.toString();
    }
}
