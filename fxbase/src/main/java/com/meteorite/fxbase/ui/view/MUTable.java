package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.component.table.cell.BoolTableCell;
import com.meteorite.fxbase.ui.component.table.cell.DictTableCell;
import com.meteorite.fxbase.ui.component.table.column.BaseTableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends BorderPane {
    private TableView<DBResult> tableView;

    private ViewLayout viewLayout;

    public MUTable(ViewLayout viewLayout) {
        this.viewLayout = viewLayout;
        initUI();
    }

    private void initUI() {
        tableView = new TableView<>();
        tableView.setEditable(true);
        // 创建表格列头信息
        createTableColumns();
        // 创建表格数据
        createTableData();
        this.setCenter(tableView);
    }

    private void createTableColumns() {
        TableProperty table = new TableProperty(viewLayout);
        for (final TableFieldProperty property : table.getFieldProperties()) {
            tableView.getColumns().add(new BaseTableColumn(property));
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
