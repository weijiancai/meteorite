package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.component.table.cell.BoolTableCell;
import com.meteorite.fxbase.ui.component.table.cell.DictTableCell;
import com.meteorite.fxbase.ui.component.table.cell.SortNumTableCell;
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

import java.util.List;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends TableView<DBResult> {
    private ViewLayout viewLayout;
    private View view;

    public MUTable(ViewLayout viewLayout) {
        this.viewLayout = viewLayout;
        initUI();
    }

    public MUTable(View view) {
        this.view = view;
        initUI();
    }

    private void initUI() {
//        tableView.setEditable(true);
        // 创建表格列头信息
        createTableColumns();
    }

    private void createTableColumns() {
        // 创建序号列
        TableColumn<DBResult, String> sortNumCol = new TableColumn<>("序号");
        sortNumCol.setPrefWidth(50);
        sortNumCol.setCellFactory(new Callback<TableColumn<DBResult, String>, TableCell<DBResult, String>>() {
            @Override
            public TableCell<DBResult, String> call(TableColumn<DBResult, String> param) {
                return new SortNumTableCell();
            }
        });
        this.getColumns().add(sortNumCol);

        // 创建其他列
        TableProperty table = new TableProperty(view);
        for (final TableFieldProperty property : table.getFieldProperties()) {
            this.getColumns().add(new BaseTableColumn(property));
        }
    }

    public void setTableData(List<DBResult> list) {
        try {
            this.setItems(FXCollections.observableArrayList(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
