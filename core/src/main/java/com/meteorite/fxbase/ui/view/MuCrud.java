package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    private MUForm queryForm;
    private Pagination pagination;

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
        Button queryBtn = new Button("查询");
        toolBar.getItems().addAll(addBtn, lookBtn, delBtn, queryBtn);

        // 新增
        addBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                openFormWin(null);
            }
        });

        // 查看
        lookBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                DataMap result = table.getSelectionModel().getSelectedItem();
                if (result == null) {
                    MUDialog.showInformation("请选择数据行！");
                    return;
                }
                openFormWin(result);
            }
        });

        // 查询
        queryBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                Meta meta = crudProperty.getQueryView().getMeta();
                meta.query(queryForm.getQueryList(), 0, 15);
                pagination.setPageCount(meta.getPageCount());
            }
        });

        // 删除
        delBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                Meta meta = crudProperty.getTableView().getMeta();
                meta.delete(table.getSelectionModel().getSelectedIndex());
            }
        });

        queryForm = new MUForm(new FormProperty(crudProperty.getQueryView()));
        box.getChildren().add(queryForm);

        return box;
    }

    private Node createCenter() {
        VBox box = new VBox();

        table = new MUTable(crudProperty.getTableView());
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    openFormWin(table.getSelectionModel().getSelectedItem());
                }
            }
        });

        pagination = new Pagination(1);
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Meta meta = crudProperty.getQueryView().getMeta();
                try {
                    meta.query(queryForm.getQueryList(), newValue.intValue(), 15);
                    pagination.setPageCount(meta.getPageCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        box.getChildren().addAll(table);

        this.setBottom(pagination);

        return table;
    }

    private void openFormWin(DataMap result) {
        MUForm form = new MUForm(new FormProperty(crudProperty.getFormView()));
        form.setValues(result);
        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
    }
}
