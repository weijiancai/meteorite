package com.meteorite.fxbase.ui.view;

import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.model.ViewLayout;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends BorderPane {
    private TableView tableView;

    private ViewLayout viewLayout;

    public MUTable(ViewLayout viewLayout) {
        this.viewLayout = viewLayout;
        initUI();
    }

    private void initUI() {
        tableView = new TableView();
        // 创建表格列头信息
        createTableColumns();
        // 创建表格数据
        createTableData();
        this.setCenter(tableView);
    }

    private void createTableColumns() {
        for (MetaField field : viewLayout.getMeta().getFields()) {
            TableColumn column = new TableColumn();
            column.setText(field.getDisplayName());
            tableView.getColumns().add(column);
        }
    }

    private void createTableData() {
        try {
            tableView.setItems(FXCollections.observableArrayList(viewLayout.getMeta().query()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
