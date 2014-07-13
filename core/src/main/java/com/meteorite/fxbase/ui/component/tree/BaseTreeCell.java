package com.meteorite.fxbase.ui.component.tree;

import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UString;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTreeCell extends TreeCell<ITreeNode> {
    private HBox box;
    private Label mainLabel;
    private Label presentableLabel;
    private ContextMenu contextMenu = new ContextMenu();

    public BaseTreeCell() {
        box = new HBox();
        mainLabel = new Label();
        presentableLabel = new Label();

        box.getChildren().addAll(mainLabel, presentableLabel);
    }

    public void addContextMenu(MenuItem item) {
        contextMenu.getItems().add(item);
    }

    @Override
    protected void updateItem(ITreeNode item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            mainLabel.setText(item.toString());
            if (item instanceof INavTreeNode) {
                INavTreeNode navNode = (INavTreeNode) item;
//                presentableLabel.setText(navNode.toString());
                String iconPath = navNode.getIcon();
                if (UString.isNotEmpty(iconPath)) {
                    Node node = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
                    mainLabel.setGraphic(node);
                }
            }
            setGraphic(box);
            setContextMenu(contextMenu);
        }
    }
}
