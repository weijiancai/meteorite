package com.meteorite.fxbase.ui.view;

import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.BasePane;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckListView;

import java.util.List;

/**
 * MetaUI ListView
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUListView<T> extends BasePane {
    private CheckListView<T> listView = new CheckListView<>();
    private List<T> data;

    public MUListView(List<T> list) {
        this.data = list;
        this.init();
    }

    @Override
    public void initUI() {
        super.initUI();
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
}
