package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.util.UString;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.BasePane;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckListView;

import java.util.List;

/**
 * MetaUI CheckListView
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUCheckListView<T> extends BorderPane implements IValue {
    private CheckListView<T> listView = new CheckListView<>();
    private List<T> data;
    private String name;

    public MUCheckListView(List<T> list) {
        this.data = list;
        this.init();
    }

    public void init() {
        listView.getItems().addAll(data);
        this.setCenter(listView);

        HBox box = new HBox(12);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(5));
        Button enableAll = new Button("选择所有");
        enableAll.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                listView.getCheckModel().selectAll();
            }
        });
        Button disableAll = new Button("取消所有");
        disableAll.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                for (int i = 0; i < data.size(); i++) {
                    listView.getCheckModel().clearSelection(i);
                }
            }
        });
        box.getChildren().addAll(enableAll, disableAll);
        this.setBottom(box);
    }

    public void selectAll() {
        listView.getCheckModel().selectAll();
    }

    @Override
    public String value() {
        return UString.convert(listView.getCheckModel().getSelectedItems());
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public StringProperty valueProperty() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
