package com.meteorite.fxbase.ui.view;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.component.tree.BaseTreeCell;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * MetaUI Tree
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTree extends TreeView<ITreeNode> {
    public MUTree(ITreeNode root) {
        MUTreeItem rootItem = new MUTreeItem(root);
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
}
