package com.meteorite.fxbase.ui.win;

import com.meteorite.core.meta.MetaManager;
import com.meteorite.fxbase.ui.component.guide.MetaCreateGuide;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * 元数据窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUMetaWin extends BorderPane {
    private MUTable metaTable;
    private Button btnDesign;
    private Button btnMetaCreate;

    public MUMetaWin() {
        initUI();
    }

    private void initUI() {
        TabPane tabPane = new TabPane();

        Tab metaItemTab = new Tab("元数据项");
        metaItemTab.setClosable(false);
        MUTable itemTable = new MUTable();
        itemTable.initUI(MetaManager.getMeta("MetaItem"));
        metaItemTab.setContent(itemTable);

        btnDesign = new Button("设计视图");
        btnMetaCreate = new Button("创建元数据");

        metaTable = new MUTable();
        metaTable.addTableButton("View", btnDesign);
        metaTable.initUI(MetaManager.getMeta("Meta"));
        metaTable.getTableToolBar().getItems().add(btnMetaCreate);

        Tab metaTab = new Tab("元数据");
        metaTab.setClosable(false);
        metaTab.setContent(metaTable);

        tabPane.getTabs().addAll(metaItemTab, metaTab);

        this.setCenter(tabPane);
        // 注册按钮
        registButtonEvents();
        // 选中元数据Tab页
        tabPane.getSelectionModel().select(1);
    }

    private void registButtonEvents() {
        btnDesign.setOnAction(null);
        btnMetaCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MetaCreateGuide guide = new MetaCreateGuide();
                MUDialog.showCustomDialog(null, "创建元数据向导", guide, null);
            }
        });
    }
}
