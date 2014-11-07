package com.meteorite.fxbase.ui.view;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * MetaUI Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTree extends TreeView<ITreeNode> {
    private Map<ITreeNode, TreeItem<ITreeNode>> nodeItemMap = new HashMap<ITreeNode, TreeItem<ITreeNode>>();

    public MUTree() {
    }

    public MUTree(ITreeNode root) {
        if (root != null) {
            MUTreeItem rootItem = new MUTreeItem(root);
            rootItem.setExpanded(true);
            this.setRoot(rootItem);
        }

        /*this.setCellFactory(new Callback<TreeView<ITreeNode>, TreeCell<ITreeNode>>() {
            @Override
            public TreeCell<ITreeNode> call(TreeView<ITreeNode> param) {
                return new BaseTreeCell();
            }
        });*/
        /*TreeItem<ITreeNode> rootItem = addTreeNode(root);*/
//        this.setShowRoot(false);
    }

    public ITreeNode getSelected() {
        TreeItem<ITreeNode> item = this.getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.getValue();
        }
        return null;
    }

    public void expandTo(ITreeNode node) {
        Stack<ITreeNode> stack = new Stack<ITreeNode>();
        ITreeNode child = node;
        ITreeNode parent;
        while ((parent = child.getParent()) != null) {
            stack.add(parent);
            child = parent;
        }

        while (!stack.isEmpty()) {
            parent = stack.pop();
            TreeItem item = nodeItemMap.get(parent);
            if (item != null && !item.isExpanded()) {
                item.setExpanded(true);
            }
        }

        this.getSelectionModel().select(nodeItemMap.get(node));
        int row = this.getRow(nodeItemMap.get(node));
        this.scrollTo(row);
    }

    public void putNodeItem(ITreeNode node, MUTreeItem item) {
        nodeItemMap.put(node, item);
    }

    public Map<ITreeNode, TreeItem<ITreeNode>> getNodeItemMap() {
        return nodeItemMap;
    }

    public TreeItem<ITreeNode> getTreeItem(ITreeNode node) {
        return nodeItemMap.get(node);
    }
}
