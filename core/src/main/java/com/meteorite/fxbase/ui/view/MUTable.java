package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.table.cell.SortNumTableCell;
import com.meteorite.fxbase.ui.component.table.column.BaseTableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends TableView<DataMap> {
    private View view;
//    private Meta meta;

    public MUTable(View view) {
        this.view = view;
//        this.meta = view.getMeta();
        initUI();
    }

    private void initUI() {
//        tableView.setEditable(true);
        // 创建表格列头信息
        createTableColumns();
        // 绑定数据
//        this.itemsProperty().bind(meta.dataListProperty());
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
        this.getColumns().add(sortNumCol);

        // 创建其他列
        TableProperty table = new TableProperty(view);
        for (final TableFieldProperty property : table.getFieldProperties()) {
            this.getColumns().add(new BaseTableColumn(property));
        }
    }
}
