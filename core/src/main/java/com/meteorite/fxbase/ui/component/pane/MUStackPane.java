package com.meteorite.fxbase.ui.component.pane;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * MetaUI StackPane
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUStackPane extends StackPane {
    public void add(Node node) {
        this.getChildren().add(node);
        if(getChildren().size() > 1) {
            node.setVisible(false);
        }
    }

    public void addAll(Node... nodes) {
        for (Node node : nodes) {
            add(node);
        }
    }

    public void show(int index) {
        for(Node node : getChildren()) {
            node.setVisible(false);
        }
        getChildren().get(index).setVisible(true);
    }
}
