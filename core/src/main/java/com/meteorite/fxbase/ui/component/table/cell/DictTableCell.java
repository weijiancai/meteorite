package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UString;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;

/**
 * 数据字典Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictTableCell extends BaseTableCell {
    private StackPane box;
    private Label label;
    private ComboBox<DictCode> comboBox;
    private final ObservableList<DictCode> items;

    private DictCategory dictCategory;

    public DictTableCell(TableColumn<DataMap, String> column, TableFieldProperty prop) {
        super(column, prop);

        box = new StackPane();
        label = new Label();

        box.setAlignment(Pos.CENTER);
        box.getChildren().add(label);

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
                    isModified.set(true);
                    if (newValue != null) {
                        commitEdit(newValue.getName());
                        valueProperty.set(newValue.getName());
                    }
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

        setGraphic(box);
    }

    /** {@inheritDoc} */
    @Override public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (UString.isNotEmpty(item)) {
            DictCode code = dictCategory.getDictCode(item);
            if (code == null) {
                code = dictCategory.getDictCodeByName(item);
            }
            if (code != null) {
                label.setText(code.getDisplayName());
            } else {
                label.setText("");
            }

            this.setGraphic(box);
        } else {
            this.setGraphic(null);
        }
    }
}
