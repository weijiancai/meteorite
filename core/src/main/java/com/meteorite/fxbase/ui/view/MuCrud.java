package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.action.MUAction;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UUIDUtil;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.table.TableExportGuide;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MetaUI CRUD视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuCrud extends StackPane {
    public static final String TOTAL_FORMAT = "总共%s条";
    private CrudProperty crudProperty;
    private MUTable table;
    private MUForm queryForm;
    private MUForm editForm;
    private Pagination pagination;
    private Hyperlink totalLink;
    private TextField pageRowsTF;

    private Map<String, Button> tableButton = new HashMap<String, Button>();

    public MuCrud(View view) {
        initUI(view);
    }

    public MuCrud() {
    }

    public void initUI(View view) {
        this.crudProperty = new CrudProperty(view);
        FormProperty queryForm = new FormProperty(crudProperty.getQueryView());
        FormProperty editForm = new FormProperty(crudProperty.getFormView());

        initUI(queryForm, editForm, null);
    }

    public void initUI(FormProperty queryFormProperty, FormProperty editFormProperty, TableProperty tableProperty) {
        this.queryForm = new MUForm(queryFormProperty);
        this.table = new MUTable();
        if (tableProperty == null && crudProperty != null) {
            this.table.initUI(crudProperty.getTableView());
        } else {
            this.table.initUI(tableProperty);
        }
        this.editForm = new MUForm(editFormProperty, table);

        editForm.setVisible(false);
        BorderPane root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());
        root.visibleProperty().bind(editForm.visibleProperty().not());

        this.getChildren().addAll(root, editForm);

        // 注册按钮
        for (MUTable table : editForm.getChildrenTables()) {
            Button button = tableButton.get(table.getConfig().getMeta().getName());
            if (button != null) {
                table.getToolBar().getItems().add(button);
            }
        }
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
        Button copyBtn = new Button("复制");
        toolBar.getItems().addAll(addBtn, lookBtn, delBtn, queryBtn, exportBtn, copyBtn);

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
                DataMap result = table.getSelectedItem();
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
                if (table.getItems() != null) {
                    table.getItems().clear();
                }
                // 查询数据
                Meta meta = queryForm.getFormConfig().getMeta();
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
                Meta meta = table.getConfig().getMeta();
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

        // 复制数据
        copyBtn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                final DataMap result = table.getSelectedItem();
                if (result == null) {
                    MUDialog.showInformation("请选择数据行！");
                    return;
                }
                final FormProperty formProperty = new FormProperty(crudProperty.getFormView());
                formProperty.setFormType(FormType.EDIT);

                // 主键值
                List<MetaField> fields = formProperty.getMeta().getPkFields();
                for (MetaField field : fields) {
                    if ("GUID()".equals(field.getDefaultValue())) {
                        result.put(field.getName(), UUIDUtil.getUUID());
                    }
                }

                final MUForm form = new MUForm(formProperty, table, false);
                form.setAdd(true);
                form.setValues(result);
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "复制", form, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {
                        try {
                            form.save();
                        } catch (Exception e) {
                            MUDialog.showExceptionDialog(e);
                        }
                        return null;
                    }
                });
            }
        });

        box.getChildren().add(queryForm);

        // Actions
        if (crudProperty != null) {
            for (final MUAction action : crudProperty.getMeta().getActionList()) {
                Button button = new Button(action.getDisplayName());
                button.setOnAction(new MuEventHandler<ActionEvent>() {
                    @Override
                    public void doHandler(ActionEvent event) throws Exception {
                        Class<?> clazz = action.getActionClass();
                        Method method = clazz.getMethod(action.getMethodName());
                        method.invoke(clazz.newInstance());
                    }
                });
                toolBar.getItems().add(button);
            }
        }

        return box;
    }

    private Node createCenter() {
        table.getSourceTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                Meta meta = table.getConfig().getMeta();
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
        if (result == null) { // 新增
            editForm.add();
        } else { // 查看
            editForm.setValues(result);
        }
        editForm.setVisible(true);
//        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
    }

    public MUTable getTable() {
        return table;
    }

    public void addTableButton(String metaName, Button button) {
        tableButton.put(metaName, button);
    }
}
