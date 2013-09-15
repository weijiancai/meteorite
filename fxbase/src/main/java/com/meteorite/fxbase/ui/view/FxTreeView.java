package com.meteorite.fxbase.ui.view;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.IViewConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxTreeView implements IView<Pane> {
    private IViewConfig viewConfig;
    private BorderPane borderPane = new BorderPane();
    private TreeView<ITreeNode> treeView = new TreeView<ITreeNode>();

    public FxTreeView(IViewConfig config) {
        this.viewConfig = config;
        initUI();
    }

    @Override
    public void initUI() {
        borderPane.setCenter(treeView);
        viewConfig.getLayoutConfig().getPropStringValue("");
    }

    @Override
    public IViewConfig getViewConfig() {
        return viewConfig;
    }

    @Override
    public Pane layout() {
        return borderPane;
    }

    // This method creates a TreeItem to represent the given File. It does this
    // by overriding the TreeItem.getChildren() and TreeItem.isLeaf() methods
    // anonymously, but this could be better abstracted by creating a
    // 'FileTreeItem' subclass of TreeItem. However, this is left as an exercise
    // for the reader.
    private TreeItem<ITreeNode> createNode(final ITreeNode node) {
        return new TreeItem<ITreeNode>(node) {
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
                    ITreeNode treeNode = (ITreeNode) getValue();
                    isLeaf = treeNode.getChildren() == null || treeNode.getChildren().size() == 0;
                }

                return isLeaf;
            }

            private ObservableList<TreeItem<ITreeNode>> buildChildren(TreeItem<ITreeNode> TreeItem) {
                ITreeNode item = TreeItem.getValue();
                if (item != null && item.getChildren() != null && item.getChildren().size() > 0) {
                    ObservableList<TreeItem<ITreeNode>> children = FXCollections.observableArrayList();

                    for (ITreeNode child : item.getChildren()) {
                        children.add(createNode(child));
                    }

                    return children;
                }

                return FXCollections.emptyObservableList();
            }
        };
    }
}
