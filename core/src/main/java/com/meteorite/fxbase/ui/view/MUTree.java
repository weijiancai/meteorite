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

/**
 * MetaUI Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTree extends TreeView<ITreeNode> {
    private Map<ITreeNode, TreeItem<ITreeNode>> nodeItemMap = new HashMap<ITreeNode, TreeItem<ITreeNode>>();

    public MUTree(ITreeNode root) {
        MUTreeItem rootItem = new MUTreeItem(this, root);
        /*this.setCellFactory(new Callback<TreeView<ITreeNode>, TreeCell<ITreeNode>>() {
            @Override
            public TreeCell<ITreeNode> call(TreeView<ITreeNode> param) {
                return new BaseTreeCell();
            }
        });*/
        /*TreeItem<ITreeNode> rootItem = addTreeNode(root);*/
        rootItem.setExpanded(true);
        this.setRoot(rootItem);
        this.setShowRoot(false);
    }

    public TreeItem<ITreeNode> addTreeNode(ITreeNode node) {
        TreeItem<ITreeNode> rootItem = new TreeItem<ITreeNode>(node);
        setIcon(rootItem);
        buildTree(node, rootItem);
        return rootItem;
    }

    private void buildTree(ITreeNode parentNode, TreeItem<ITreeNode> parentItem) {
        List<? extends ITreeNode> children = parentNode.getChildren();
        if (children != null && children.size() > 0) {
            for (ITreeNode node : children) {
                TreeItem<ITreeNode> item = new TreeItem<ITreeNode>(node);
                setIcon(item);
                parentItem.getChildren().add(item);
                nodeItemMap.put(node, item);
                buildTree(node, item);
            }
        }
    }

    private void setIcon(TreeItem<ITreeNode> item) {
        String iconPath = item.getValue().getIcon();
        if (UString.isNotEmpty(iconPath)) {
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
            item.setGraphic(imageView);
        }
    }


    public ITreeNode getSelected() {
        TreeItem<ITreeNode> item = this.getSelectionModel().getSelectedItem();
        if (item != null) {
            return item.getValue();
        }
        return null;
    }

    public void expandTo(ITreeNode node) {
        /*Stack<ITreeNode> stack = new Stack<>();
        ITreeNode child = node;
        ITreeNode parent;
        while ((parent = node.getParent()) != null) {
            stack.add(parent);
            node = parent;
        }

        while (!stack.isEmpty()) {
            parent = stack.pop();
            TreeItem item = nodeItemMap.get(parent);
            if (item != null && !item.isExpanded()) {
                item.setExpanded(true);
            }
        }*/

        this.getSelectionModel().select(nodeItemMap.get(node));
        /*int row = this.getRow(nodeItemMap.get(node));
        this.scrollTo(row);*/
    }

    public void putNodeItem(ITreeNode node, MUTreeItem item) {
        nodeItemMap.put(node, item);
    }
}
