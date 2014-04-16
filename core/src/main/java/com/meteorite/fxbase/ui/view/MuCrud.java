package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.BaseApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialogs;

/**
 * MetaUI CRUD视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuCrud extends BorderPane {
    private View view;
    private CrudProperty crudProperty;
    private MUTable table;

    public MuCrud(View view) {
        this.view = view;
        this.crudProperty = new CrudProperty(view);
        initUI();
    }

    private void initUI() {
        this.setTop(createTop());
        this.setCenter(createCenter());
    }

    private Node createTop() {
        VBox box = new VBox();
        ToolBar toolBar = new ToolBar();
        box.getChildren().add(toolBar);

        Button addBtn = new Button("增加");
        Button lookBtn = new Button("查看");
        Button delBtn = new Button("删除");
        toolBar.getItems().addAll(addBtn, lookBtn, delBtn);

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openFormWin(null);
            }
        });

        lookBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBResult result = table.getSelectionModel().getSelectedItem();
                if (result == null) {
                    MUDialog.showInformation("请选择数据行！");
                    return;
                }
                openFormWin(result);
            }
        });

        return box;
    }

    private Node createCenter() {
        table = new MUTable(crudProperty.getTableView());
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    openFormWin(table.getSelectionModel().getSelectedItem());
                }
            }
        });
        return table;
    }

    private void openFormWin(DBResult result) {
        MUForm form = new MUForm(new FormProperty(crudProperty.getFormView()));
        form.setValues(result);
        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
    }
}
