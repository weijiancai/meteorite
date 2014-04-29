package com.meteorite.fxbase.ui.view;

import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.tree.FileTreeItem;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabs 桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane {
    private ToolBar toolBar;
    private MUTree tree;
    private MUTabPane tabPane;
    private Map<String, Tab> tabCache = new HashMap<>();

    public MUTabsDesktop(INavTreeNode navTree) {
        toolBar = new ToolBar();
        tree = new MUTree(navTree);
        tabPane = new MUTabPane();

        this.setTop(toolBar);
        this.setLeft(tree);
        this.setCenter(tabPane);

        TreeView<File> fileTree = new TreeView<>();
        fileTree.setRoot(new FileTreeItem(new File("/")));
        this.setRight(fileTree);

        initUI();
    }

    private void initUI() {
        Button btnAddDs = new Button("添加数据源");
        btnAddDs.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                MUForm form = new MUForm(new FormProperty(ViewManager.getViewByName("DBDataSourceFormView")));
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "新增数据源", form, null);
            }
        });
        toolBar.getItems().add(btnAddDs);

        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    INavTreeNode node = (INavTreeNode) tree.getSelected();
                    if (node != null) {
                        View view = node.getView();
                        if (view != null) {
                            String text = node.getName();
                            Tab tab = tabCache.get(text);
                            if (tab == null) {
                                tab = new Tab(text);
                                tab.setContent(new MuCrud(view));
                                tabPane.getTabs().add(tab);
                                tabCache.put(text, tab);
                            }
                            tabPane.getSelectionModel().select(tab);
                        }
                    }
                }
            }
        });

        tabPane.getTabs().addListener(new ListChangeListener<Tab>() {
            @Override
            public void onChanged(Change<? extends Tab> change) {
                if(change.next() && change.wasRemoved()) {
                    List<? extends Tab> removed = change.getRemoved();
                    if(removed.size() > 0) {
                        for (Tab tab : removed) {
                            tabCache.remove(tab.getText());
                        }
                    }
                }
            }
        });
    }
}
