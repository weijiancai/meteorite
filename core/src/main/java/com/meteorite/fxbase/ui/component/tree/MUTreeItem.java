package com.meteorite.fxbase.ui.component.tree;

import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTreeItem extends TreeItem<ITreeNode> {
    // We cache whether the File is a leaf or not. A File is a leaf if
    // it is not a directory and does not have any files contained within
    // it. We cache this as isLeaf() is called often, and doing the
    // actual check on File is expensive.
    private boolean isLeaf;

    // We do the children and leaf testing only once, and then set these
    // booleans to false so that we do not check again during this
    // run. A more complete implementation may need to handle more
    // dynamic file system situations (such as where a folder has files
    // added after the TreeView is shown). Again, this is left as an
    // exercise for the reader.
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public MUTreeItem() {
    }

    public MUTreeItem(ITreeNode value) {
        super(value);
        if(value instanceof INavTreeNode) {
            INavTreeNode treeNode = (INavTreeNode) value;
            String iconPath = treeNode.getIcon();
            if (UString.isNotEmpty(iconPath)) {
                Node node = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
                this.setGraphic(node);
            }
        }
    }

    @Override public ObservableList<TreeItem<ITreeNode>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

            // First getChildren() call, so we actually go off and
            // determine the children of the File contained in this TreeItem.
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            ITreeNode treeNode = getValue();
            isLeaf = treeNode.getChildren() == null || treeNode.getChildren().size() == 0;
        }

        return isLeaf;
    }

    private ObservableList<TreeItem<ITreeNode>> buildChildren(TreeItem<ITreeNode> TreeItem) {
        ITreeNode item = TreeItem.getValue();
        if (item != null && item.getChildren() != null && item.getChildren().size() > 0) {
            ObservableList<TreeItem<ITreeNode>> children = FXCollections.observableArrayList();

            for (ITreeNode child : item.getChildren()) {
                children.add(new MUTreeItem(child));
            }

            return children;
        }

        return FXCollections.emptyObservableList();
    }
}