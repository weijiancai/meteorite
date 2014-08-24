package com.meteorite.fxbase.ui;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.ResourceTreeAdapter;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.MetaPane;
import com.meteorite.fxbase.ui.view.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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
public class FxDesktop extends BorderPane implements IDesktop {
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
        TreeItem<Hyperlink> root = new TreeItem<Hyperlink>(new Hyperlink("Root"));
        Hyperlink metaLink = new Hyperlink("元数据");
        root.getChildren().add(new TreeItem<Hyperlink>(metaLink));

        Hyperlink dictLink = new Hyperlink("表格测试");
        dictLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                View view = ViewManager.getViewByName("DzCodeTableView");
                MUTable table = new MUTable(view);
                setCenter(table);
            }
        });
        root.getChildren().add(new TreeItem<Hyperlink>(dictLink));

        Hyperlink formLink = new Hyperlink("表单测试");
        formLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                View view = ViewManager.getViewByName("DzCodeFormView");
                MUForm form = new MUForm(new FormProperty(view));
                setCenter(form);
            }
        });
        root.getChildren().add(new TreeItem<Hyperlink>(formLink));

        Hyperlink queryLink = new Hyperlink("查询测试");
        queryLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                View view = ViewManager.getViewByName("DzCodeQueryView");
                MUForm form = new MUForm(new FormProperty(view));
                setCenter(form);
            }
        });
        root.getChildren().add(new TreeItem<Hyperlink>(queryLink));

        Hyperlink crudLink = new Hyperlink("CRUD");
        crudLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                View view = ViewManager.getViewByName("DzCodeCrudView");
                MuCrud crud = new MuCrud(view);
                setCenter(crud);
            }
        });
        root.getChildren().add(new TreeItem<Hyperlink>(crudLink));

        TreeView<Hyperlink> tree = new TreeView<Hyperlink>();
        tree.setRoot(root);
        tree.setShowRoot(false);
        this.setLeft(tree);
        this.setCenter(new MetaPane());

        DataSource dataSource = DataSourceManager.getSysDataSource();
        MUTree nav = null;
        try {
            INavTreeNode rootNode = new ResourceTreeAdapter(dataSource.getRootResource());
            nav = new MUTree(rootNode);
            nav.setShowRoot(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setRight(nav);
    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return null;
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
