package com.meteorite.fxbase;

import com.meteorite.core.meta.MetaManager;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxTestDesktop extends BorderPane implements IDesktop {

    @Override
    public void initUI() {
        TreeView<String> treeView = new TreeView<String>();
        this.setLeft(treeView);

        TreeItem<String> root = new TreeItem<String>("Root");
        root.setExpanded(true);
        TreeItem<String> muTableItem = new TreeItem<String>("MUTable");
        TreeItem<String> treeItem = new TreeItem<String>("TreeView测试");
        root.getChildren().add(muTableItem);
        root.getChildren().add(treeItem);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                if ("MUTable".equals(newValue.getValue())) {
                    MUTable table = new MUTable();
                    table.initUI(MetaManager.getMeta("Category"));
                    setCenter(table);
                } else if ("TreeView测试".equals(newValue.getValue())) {
                    BorderPane pane = new BorderPane();
                    final TreeView<String> tree = new TreeView<String>();
                    pane.setLeft(tree);
                    tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
                        @Override
                        public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                            System.out.println(newValue);
                        }
                    });

                    Button btnAddRoot = new Button("添加根节点");
                    btnAddRoot.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            TreeItem<String> root = new TreeItem<String>("Root");
                            tree.setRoot(root);
                        }
                    });

                    Button btnAddChild1 = new Button("添加子节点1");
                    btnAddChild1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            TreeItem<String> child = new TreeItem<String>("子节点1");
                            tree.getRoot().setExpanded(true);
                            child.setExpanded(true);
                            tree.getRoot().getChildren().add(child);

                        }
                    });

                    Button btnAddChild2 = new Button("添加孙子节点2");
                    btnAddChild2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            TreeItem<String> child = new TreeItem<String>("孙子节点1");
                            tree.getRoot().getChildren().get(0).getChildren().add(child);
                        }
                    });

                    ToolBar toolBar = new ToolBar();
                    toolBar.getItems().addAll(btnAddRoot, btnAddChild1, btnAddChild2);
                    pane.setTop(toolBar);

                    setCenter(pane);
                }
            }
        });

        treeView.setRoot(root);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return null;
    }
}
