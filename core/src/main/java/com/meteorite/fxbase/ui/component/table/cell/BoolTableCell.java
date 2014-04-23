package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumBoolean;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UString;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;

/**
 * Boolean Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BoolTableCell extends BaseTableCell {
    private StackPane box;
    private Label label;
    private ComboBox<DictCode> comboBox;

    private DictCategory dictCategory = DictManager.getDict(EnumBoolean.class);

    public BoolTableCell(TableColumn<DataMap, String> column, TableFieldProperty prop) {
        super(column, prop);

        box = new StackPane();
        label = new Label();
        label.setStyle("-fx-text-fill: #222222");

        box.setAlignment(Pos.CENTER);
        box.getChildren().add(label);
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if (UString.isNotEmpty(s)) {
            boolean isTrue = UString.toBoolean(s);
            if(isTrue) {
                label.setText("是");
                box.setStyle("-fx-padding: 3;-fx-background-color:#2BA812;");
            } else {
                label.setText("否");
                box.setStyle("-fx-padding: 3;-fx-background-color:#ffffff;");
            }

            this.setGraphic(box);
        } else {
            this.setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        if (comboBox == null) {
            comboBox = new ComboBox<>();
            comboBox.setItems(FXCollections.observableArrayList(dictCategory.getCodeList()));
            comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DictCode>() {
                @Override
                public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                    commitEdit(newValue.getName());
                }
            });
            comboBox.minWidthProperty().bind(this.widthProperty());
        }

        String codeId = EnumBoolean.F.name();
        if (UString.toBoolean(getItem())) {
            codeId = EnumBoolean.T.name();
        }
        DictCode code = dictCategory.getDictCode(codeId);
        comboBox.getSelectionModel().select(code);

        super.startEdit();
        setText(null);
        setGraphic(comboBox);
    }

    @Override public void cancelEdit() {
        super.cancelEdit();

        /*DictCode code = comboBox.getSelectionModel().getSelectedItem();
        if (code != null) {
            setItem(code.getName());
        }*/
        setGraphic(box);
    }
}
