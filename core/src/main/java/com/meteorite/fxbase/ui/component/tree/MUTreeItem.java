package com.meteorite.fxbase.ui.component.tree;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.view.MUTree;
import com.meteorite.fxbase.util.FUImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

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
    private MUTree tree;

    public MUTreeItem() {
    }

    public MUTreeItem(MUTree tree, ITreeNode value) {
        super(value);
        this.tree = tree;
        if(value != null) {
            String iconPath = value.getIcon();
            if (UString.isNotEmpty(iconPath)) {
                Node node = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
                this.setGraphic(node);
            }

            /*if (value.getChildren().size() == 0 && value instanceof BaseTreeNode) {
                BaseTreeNode node = (BaseTreeNode) value;
                node.childrenProperty().get().addListener(new ListChangeListener<ITreeNode>() {
                    @Override
                    public void onChanged(Change<? extends ITreeNode> c) {
                        getChildren().setAll(buildChildren(MUTreeItem.this));
                        setExpanded(true);
                    }
                });
            }*/
        }

        tree.putNodeItem(value, this);
    }

    public MUTreeItem(ITreeNode treeNode) {
        super(treeNode, FUImage.getImageView(treeNode.getIcon()));
        // 构造子节点
        List<? extends ITreeNode> children = treeNode.getChildren();
        if (children != null && children.size() > 0) {
            for (ITreeNode node : children) {
                MUTreeItem item = new MUTreeItem(node);
                this.getChildren().add(item);
            }
        }
    }

    /*@Override public ObservableList<TreeItem<ITreeNode>> getChildren() {
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
                children.add(new MUTreeItem(tree, child));
            }

            return children;
        }

        return FXCollections.emptyObservableList();
    }*/
}
