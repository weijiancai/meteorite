package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UNumber;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.table.TableExportGuide;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * MetaUI CRUD视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuCrud extends StackPane {
    public static final String TOTAL_FORMAT = "总共%s条";
    private CrudProperty crudProperty;
    private BorderPane root;
    private MUTable table;
    private MUForm queryForm;
    private MUForm editForm;
    private Pagination pagination;
    private Hyperlink totalLink;
    private TextField pageRowsTF;

    public MuCrud(View view) {
        this.crudProperty = new CrudProperty(view);
        initUI();
    }

    private void initUI() {
        root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());

        editForm = new MUForm(new FormProperty(crudProperty.getFormView()), table);
        editForm.setVisible(false);
        root.visibleProperty().bind(editForm.visibleProperty().not());

        this.getChildren().addAll(root, editForm);
    }

    private Node createTop() {
        VBox box = new VBox();
        ToolBar toolBar = new ToolBar();
        box.getChildren().add(toolBar);

        Button addBtn = new Button("增加");
        Button lookBtn = new Button("查看");
        Button delBtn = new Button("删除");
        Button queryBtn = new Button("查询");
        Button exportBtn = new Button("导出");
        toolBar.getItems().addAll(addBtn, lookBtn, delBtn, queryBtn, exportBtn);

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
                // 清空数据
                table.getItems().clear();
                // 查询数据
                Meta meta = crudProperty.getQueryView().getMeta();
                QueryResult<DataMap> queryResult = meta.query(queryForm.getQueryList(), 0, UNumber.toInt(pageRowsTF.getText()));
                table.getItems().addAll(queryResult.getRows());
                pagination.setPageCount(meta.getPageCount());
                totalLink.setText(String.format(TOTAL_FORMAT, meta.getTotalRows()));
            }
        });

        // 删除
        delBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                Meta meta = crudProperty.getTableView().getMeta();
                int selected = table.getSelectionModel().getSelectedIndex();
                meta.delete(selected);
                table.getItems().remove(selected);
            }
        });

        // 导出数据
        exportBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                TableExportGuide guide = new TableExportGuide(table);
                MUDialog.showCustomDialog(null, "导出数据向导", guide, null);
            }
        });

        queryForm = new MUForm(new FormProperty(crudProperty.getQueryView()));
        box.getChildren().add(queryForm);

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

    private Node createBottom() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);

        pagination = new Pagination(1);
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Meta meta = crudProperty.getQueryView().getMeta();
                try {
                    QueryResult<DataMap> queryResult = meta.query(queryForm.getQueryList(), newValue.intValue(), 15);
                    table.getItems().clear();
                    table.getItems().addAll(queryResult.getRows());
                    pagination.setPageCount(meta.getPageCount());
                    totalLink.setText(String.format(TOTAL_FORMAT, meta.getTotalRows()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        totalLink = new Hyperlink(String.format(TOTAL_FORMAT, 0));
        Label pageRowsLabel = new Label("，每页显示");
        pageRowsTF = new TextField("15");

        box.getChildren().addAll(totalLink, pageRowsLabel, pageRowsTF, new Label("条"), pagination);

        return box;
    }

    private void openFormWin(DataMap result) {
        editForm.reset();
        editForm.setValues(result);
        editForm.setVisible(true);
//        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
    }
}
