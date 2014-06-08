package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.BaseProperty;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.table.cell.SortNumTableCell;
import com.meteorite.fxbase.ui.component.table.column.BaseTableColumn;
import com.meteorite.fxbase.ui.event.DataChangeEvent;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends BorderPane {
    private View view;
//    private Meta meta;
    private TableProperty config;
    private TableView<DataMap> table;
    private ToolBar toolBar;

    public MUTable(View view) {
        this.view = view;
//        this.meta = view.getMeta();
        initUI();
    }

    private void initUI() {
        table = new TableView<>();
        toolBar = new ToolBar();
//        tableView.setEditable(true);
        // 创建工具条
        createToolbar();
        // 创建表格列头信息
        createTableColumns();
        // 绑定数据
//        this.itemsProperty().bind(meta.dataListProperty());
        this.setTop(toolBar);
        this.setCenter(table);
    }

    private void createTableColumns() {
        // 创建序号列
        TableColumn<DataMap, String> sortNumCol = new TableColumn<>("序号");
        sortNumCol.setPrefWidth(50);
        sortNumCol.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
            @Override
            public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                return new SortNumTableCell();
            }
        });
        table.getColumns().add(sortNumCol);

        // 创建其他列
        config = new TableProperty(view);
        for (final TableFieldProperty property : config.getFieldProperties()) {
            table.getColumns().add(new BaseTableColumn(property));
        }
    }

    private void createToolbar() {
        // 列信息
        Button btnCol = new Button("列信息");
        btnCol.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                final View view = ViewManager.getViewByName("TableFieldPropertyTableView");
                MUTable root = new MUTable(view);
                root.showToolbar(false);
                root.setEditable(true);
                List<DataMap> list = new ArrayList<>();
                for (final TableFieldProperty property : config.getFieldProperties()) {
                    DataMap data = new DataMap();
                    for (ViewProperty prop : view.getViewProperties()) {
                        String id = prop.getProperty().getId();
                        String name = prop.getProperty().getName();
                        data.put(name, property.getPropertyValue(id));
                    }
                    list.add(data);
                }
                root.addEventHandler(DataChangeEvent.EVENT_TYPE, new MuEventHandler<DataChangeEvent>() {
                    @Override
                    public void doHandler(DataChangeEvent event) throws Exception {
                        String fieldName = event.getRowData().getString("name");
                        MetaField field = MUTable.this.view.getMeta().getFieldByName(fieldName.toLowerCase());
                        ViewProperty viewProperty = MUTable.this.view.getViewProperty(field, event.getName());
                        viewProperty.setValue(event.getNewValue());
                        config.setPropertyValue(field, event.getName(), event.getNewValue());
                    }
                });
                root.getItems().addAll(list);
                MUDialog.showCustomDialog(null, "列信息", root, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {

                        return null;
                    }
                });
            }
        });
        toolBar.getItems().addAll(btnCol);
    }

    public TableProperty getConfig() {
        return config;
    }

    public View getView() {
        return view;
    }

    public DataMap getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    public ObservableList<DataMap> getItems() {
        return table.getItems();
    }

    public TableSelectionModel<DataMap> getSelectionModel() {
        return table.getSelectionModel();
    }

    public TableView<DataMap> getSourceTable() {
        return table;
    }

    public void showToolbar(boolean isShow) {
        if (isShow) {
            setTop(toolBar);
        } else {
            setTop(null);
        }
    }

    public void setEditable(boolean editable) {
        if(editable) {
            table.setEditable(true);
        } else {
            table.setEditable(false);
        }
    }
}
