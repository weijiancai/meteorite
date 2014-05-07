package com.meteorite.fxbase.ui.view;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.component.tree.BaseTreeCell;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import java.util.*;

/**
 * MetaUI Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTree extends TreeView<ITreeNode> {
    private Map<ITreeNode, MUTreeItem> nodeItemMap = new HashMap<>();

    public MUTree(ITreeNode root) {
        MUTreeItem rootItem = new MUTreeItem(this, root);
        this.setRoot(rootItem);
        rootItem.setExpanded(true);
        /*this.setCellFactory(new Callback<TreeView<ITreeNode>, TreeCell<ITreeNode>>() {
            @Override
            public TreeCell<ITreeNode> call(TreeView<ITreeNode> param) {
                return new BaseTreeCell();
            }
        });*/
    }

    public ITreeNode getSelected() {
        TreeItem<ITreeNode> item = this.getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.getValue();
        }
        return null;
    }

    public void expandTo(ITreeNode node) {
        Stack<ITreeNode> stack = new Stack<>();
        ITreeNode child = node;
        ITreeNode parent;
        while ((parent = node.getParent()) != null) {
            stack.add(parent);
            node = parent;
        }

        while (!stack.isEmpty()) {
            parent = stack.pop();
            MUTreeItem item = nodeItemMap.get(parent);
            if (item != null && !item.isExpanded()) {
                item.setExpanded(true);
            }
        }

        /*int row = this.getRow(nodeItemMap.get(node));
        this.scrollTo(row);*/
    }

    public void putNodeItem(ITreeNode node, MUTreeItem item) {
        nodeItemMap.put(node, item);
    }
}
