package com.meteorite.fxbase.ui.component;

import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ViewConfigFactory;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.view.FxPane;
import com.meteorite.fxbase.ui.view.FxViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaPane extends BorderPane {
    private TableView tableView = new TableView();

    public MetaPane() {
        HBox top = new HBox();
        Button addBtn = new Button("添加元数据");
        addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                final IView<FxPane> view = FxViewFactory.getView(ViewConfigFactory.createFormConfig(MetaManager.getMeta(MetaField.class)));

                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "添加数据源", view.layout(), new Callback<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        FxPane pane = view.layout();
                        Map<String, String> map = pane.getValueMap();
                        MetaField metaField = new MetaField();
                        String displayName = map.get("DisplayName");
                        if (UString.isEmpty(displayName)) {
                            return null;
                        }
                        metaField.setDisplayName(displayName);
                        try {
                            MetaManager.addMetaField(metaField);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        initTableData();
                        return null;
                    }
                });
            }
        });

        Button genBtn = new Button("生成表单");
        genBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // 打开生成表单面板

            }
        });
        top.getChildren().addAll(addBtn, genBtn);
        this.setTop(top);


        TableColumn idCol = new TableColumn();
        idCol.setText("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn nameCol = new TableColumn();
        nameCol.setText("名称");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn displayNameCol = new TableColumn();
        displayNameCol.setText("显示名");
        displayNameCol.setCellValueFactory(new PropertyValueFactory<>("displayName"));

        TableColumn dataTypeCol = new TableColumn();
        dataTypeCol.setText("数据类型");
        dataTypeCol.setCellValueFactory(new PropertyValueFactory<>("dataType"));

        TableColumn descCol = new TableColumn();
        descCol.setText("描述");
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));

        TableColumn defaultValueCol = new TableColumn();
        defaultValueCol.setText("默认值");
        defaultValueCol.setCellValueFactory(new PropertyValueFactory<>("defaultValue"));

        TableColumn dictCol = new TableColumn();
        dictCol.setText("数据字典");
        dictCol.setCellValueFactory(new PropertyValueFactory<>("dictId"));

        TableColumn isValidCol = new TableColumn();
        isValidCol.setText("是否有效");
        isValidCol.setCellValueFactory(new PropertyValueFactory<>("isValid"));

        TableColumn sortNumCol = new TableColumn();
        sortNumCol.setText("排序号");
        sortNumCol.setCellValueFactory(new PropertyValueFactory<>("sortNum"));

        TableColumn inputDateCol = new TableColumn();
        inputDateCol.setText("录入时间");
        inputDateCol.setCellValueFactory(new PropertyValueFactory<>("inputDate"));


        tableView.getColumns().addAll(idCol, nameCol, displayNameCol, dataTypeCol, descCol, defaultValueCol, dictCol, isValidCol, sortNumCol, inputDateCol);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.setCenter(tableView);
        initTableData();
    }

    private void initTableData() {
        ObservableList<MetaField> data = FXCollections.observableArrayList(MetaManager.getMetaFieldList());
        tableView.setItems(data);
    }
}
