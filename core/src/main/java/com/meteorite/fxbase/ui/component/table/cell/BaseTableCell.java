package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.dict.EnumAlign;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTableCell extends TableCell<DBResult, String> {
    protected TableColumn<DBResult, String> tableColumn;
    protected TableFieldProperty prop;

    public BaseTableCell(TableColumn<DBResult, String> column, TableFieldProperty prop) {
        this.tableColumn = column;
        this.prop = prop;
        Pos align = Pos.CENTER_LEFT;
        if (EnumAlign.CENTER == prop.getAlign()) {
            align = Pos.CENTER;
        } else if (EnumAlign.RIGHT == prop.getAlign()) {
            align = Pos.CENTER_RIGHT;
        }
        this.setAlignment(align);
    }

    public BaseTableCell() {
        super();
    }
}
