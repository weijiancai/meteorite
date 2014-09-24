package com.meteorite.fxbase.ui.win;

import com.meteorite.core.ui.ViewManager;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.event.MUTableEvent;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.event.ActionEvent;
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
    private MuCrud metaCrud;
    private Button btnDesign;

    public MUMetaWin() {
        initUI();
    }

    private void initUI() {
        TabPane tabPane = new TabPane();

        Tab metaItemTab = new Tab("元数据项");
        metaItemTab.setClosable(false);
        metaItemTab.setContent(new MuCrud(ViewManager.getViewByName("MetaItemCrudView")));

        btnDesign = new Button("设计视图");
        metaCrud = new MuCrud();
        metaCrud.addTableButton("View", btnDesign);
        metaCrud.initUI(ViewManager.getViewByName("MetaCrudView"));

        Tab metaTab = new Tab("元数据");
        metaTab.setClosable(false);
        metaTab.setContent(metaCrud);

        tabPane.getTabs().addAll(metaItemTab, metaTab);

        this.setCenter(tabPane);
        // 注册按钮
        registButtonEvents();
    }

    private void registButtonEvents() {
        btnDesign.setOnAction(null);

    }
}
