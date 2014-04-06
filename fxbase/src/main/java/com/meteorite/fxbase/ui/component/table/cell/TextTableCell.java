package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.db.util.DBResult;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 文本 Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TextTableCell extends BaseTableCell {
    private Label label;
    private TextField textField;

    private String oldValue;

    public TextTableCell(TableColumn<DBResult, String> column, TableFieldProperty prop) {
        super(column, prop);
        label = new Label();
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        label.setText(item);
        setGraphic(label);
    }

    @Override
    public void startEdit() {
        if (textField == null) {
            textField = new TextField();
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
        oldValue = getItem();
        textField.setText(getItem());

        super.startEdit();
        setGraphic(textField);
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setItem(oldValue);
        label.setText(oldValue);
        setGraphic(label);
    }
}
