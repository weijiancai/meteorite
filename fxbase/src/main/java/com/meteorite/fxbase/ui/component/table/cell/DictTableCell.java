package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 * 数据字典Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictTableCell extends BaseTableCell {
    private ComboBox<DictCode> comboBox;
    private final ObservableList<DictCode> items;

    private DictCategory dictCategory;

    public DictTableCell(TableColumn<DBResult, String> column, TableFieldProperty prop) {
        super(column, prop);
        dictCategory = DictManager.getDict(prop.getDict().getId());
        items = FXCollections.observableArrayList(dictCategory.getCodeList());
    }


    /** {@inheritDoc} */
    @Override public void startEdit() {
        /*if (! isEditable() || ! getTableView().isEditable() || ! getTableColumn().isEditable()) {
            return;
        }*/

        if (comboBox == null) {
            comboBox = new ComboBox<>(items);
//            comboBox.editableProperty().bind(comboBoxEditableProperty());
            comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DictCode>() {
                @Override
                public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                    commitEdit(newValue.getName());
                }
            });
        }

        DictCode code = dictCategory.getDictCode(getItem());
        if (code != null) {
            comboBox.getSelectionModel().select(code);
        }

        super.startEdit();
        setText(null);
        setGraphic(comboBox);
    }

    /** {@inheritDoc} */
    @Override public void cancelEdit() {
        super.cancelEdit();

        DictCode code = dictCategory.getDictCode(getItem());
        if (code != null) {
            setText(code.getDisplayName());
            setItem(code.getDisplayName());
        }
        setGraphic(null);
    }

    /** {@inheritDoc} */
    @Override public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            DictCode code = dictCategory.getDictCode(item);
            if (code != null) {
                setItem(code.getDisplayName());
            }
        }
    }
}
