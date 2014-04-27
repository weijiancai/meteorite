package com.meteorite.fxbase.ui.component.tree;

import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTreeCell extends TreeCell<ITreeNode> {
    private Label mainLabel;
    private Label presentableLabel;

    public BaseTreeCell() {
        HBox box = new HBox();
        mainLabel = new Label();
        presentableLabel = new Label();

        box.getChildren().addAll(mainLabel, presentableLabel);
        this.setGraphic(box);
    }

    @Override
    protected void updateItem(ITreeNode item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            mainLabel.setText(item.getName());
            if (item instanceof INavTreeNode) {
                INavTreeNode navNode = (INavTreeNode) item;
                presentableLabel.setText(navNode.getPresentableText());
                Node node = new ImageView(new Image(getClass().getResourceAsStream(navNode.getIcon())));
                mainLabel.setGraphic(node);
            }
        }
    }
}
