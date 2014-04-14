package com.meteorite.fxbase.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

/**
 * @author weijiancai
 */
public class Workbench extends StackPane {
    private TabPane tabPane;

    public Workbench() {
        tabPane = new TabPane();
        this.getChildren().add(tabPane);
    }

    /*public void addWorkspace(final WorkSpace workspace) {
        Tab tab = getTab(workspace.getTitle());
        if (tab == null) {
            tab = new Tab(workspace.getTitle());
            tab.setContent(workspace);
            tabPane.getTabs().add(tab);
        }

        tabPane.getSelectionModel().select(tab);
    }*/

    private Tab getTab(String title) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(title)) {
                return tab;
            }
        }

        return null;
    }
}
