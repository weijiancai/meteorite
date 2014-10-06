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
 * 视图管理窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUViewWin extends BorderPane {
    private MUTable viewTable;
    private Button btnDesign;
    private Button btnViewCreate;

    public MUViewWin() {
        initUI();
    }

    private void initUI() {
        btnDesign = new Button("设计视图");
        btnViewCreate = new Button("创建视图");

        viewTable = new MUTable();
        viewTable.initUI(MetaManager.getMeta("View"));
        viewTable.getTableToolBar().getItems().addAll(btnDesign, btnViewCreate);


        this.setCenter(viewTable);
        // 注册按钮
        registButtonEvents();
    }

    private void registButtonEvents() {
        btnDesign.setOnAction(null);
        btnViewCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MetaCreateGuide guide = new MetaCreateGuide();
                MUDialog.showCustomDialog(null, "创建元数据向导", guide, null);
            }
        });
    }
}
