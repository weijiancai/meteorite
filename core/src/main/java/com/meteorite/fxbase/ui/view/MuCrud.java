package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.BaseApp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * MetaUI CRUD视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuCrud extends BorderPane {
    private View view;

    public MuCrud(View view) {
        this.view = view;
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

        return box;
    }

    private Node createCenter() {
        final MUTable table = new MUTable(view.getTableLayout());
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    MUForm form = new MUForm(new FormProperty(view.getFormLayout()));
                    form.setValues(table.getSelectionModel().getSelectedItem());
                    MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
                }
            }
        });
        return table;
    }
}
