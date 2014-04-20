package com.meteorite.fxbase.ui.component.table.cell;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SortNumTableCell extends BaseTableCell {
    private Label label;

    public SortNumTableCell() {
        HBox box = new HBox();
        label = new Label();
        label.setStyle("-fx-font-weight: bolder;");

        box.setAlignment(Pos.CENTER);
        box.getChildren().add(label);

        this.setGraphic(box);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (this.getIndex() < this.getTableView().getItems().size()) {
            label.setText((this.getIndex() + 1) + "");
        }
    }
}
