package com.meteorite.fxbase.ui;

import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.MetaPane;
import com.meteorite.fxbase.ui.view.FxTreeView;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxDesktop extends BorderPane {
    private Banner banner;
    private Workbench workbench;
//    private ModuleMenu moduleMenu;
    private FxTreeView treeView;

    public FxDesktop(final Stage stage) {
        banner = new Banner(stage);
//        this.setTop(banner);
        /*moduleMenu = new ModuleMenu(this);
        this.setLeft(moduleMenu);*/
        treeView = new FxTreeView(null);
//        this.setLeft(treeView.layout());
        workbench = new Workbench();
        this.setCenter(workbench);
        initUI();
    }

    public void initUI() {
        TreeItem<Hyperlink> root = new TreeItem<>(new Hyperlink("Root"));
        Hyperlink metaLink = new Hyperlink("元数据");
        root.getChildren().add(new TreeItem<>(metaLink));

        Hyperlink dictLink = new Hyperlink("数据字典");
        dictLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                View view = ViewManager.getViewByName("DzCategoryView");
                MUTable table = new MUTable(view.getTableLayout());
                setCenter(table);
            }
        });
        root.getChildren().add(new TreeItem<Hyperlink>(dictLink));

        TreeView<Hyperlink> tree = new TreeView<>();
        tree.setRoot(root);
        tree.setShowRoot(false);
        this.setLeft(tree);
        this.setCenter(new MetaPane());
    }

    /*public void showDialog(Dialog dialog) {
        if (workbench.getChildren().contains(dialog)) {
            return;
        }
        dialog.setDesktop(this);
        if (dialog.isModal()) {
            workbench.getChildren().add(dialog.getModalPane());
        }
        workbench.getChildren().add(dialog);
        dialog.autosize();

        dialog.layoutXProperty().bind(this.widthProperty().add(-dialog.getWidth()).divide(2));
        dialog.layoutYProperty().bind(this.heightProperty().add(-dialog.getHeight()).divide(2));
    }

    public void closeDialog(Dialog dialog) {
        workbench.getChildren().removeAll(dialog.getModalPane(), dialog);
    }*/

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    /*public ModuleMenu getModuleMenu() {
        return moduleMenu;
    }

    public void setModuleMenu(ModuleMenu moduleMenu) {
        this.moduleMenu = moduleMenu;
    }*/

    public Workbench getWorkbench() {
        return workbench;
    }

    public void setWorkbench(Workbench workbench) {
        this.workbench = workbench;
    }
}
