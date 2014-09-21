package com.meteorite.fxbase.ui.win;

import com.meteorite.core.ui.ViewManager;
import com.meteorite.fxbase.ui.view.MuCrud;
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

    public MUMetaWin() {
        initUI();
    }

    private void initUI() {
        TabPane tabPane = new TabPane();

        Tab metaItemTab = new Tab("元数据项");
        metaItemTab.setClosable(false);
        metaItemTab.setContent(new MuCrud(ViewManager.getViewByName("MetaItemCrudView")));

        Tab metaTab = new Tab("元数据");
        metaTab.setClosable(false);
        metaTab.setContent(new MuCrud(ViewManager.getViewByName("MetaCrudView")));

        tabPane.getTabs().addAll(metaItemTab, metaTab);

        this.setCenter(tabPane);
    }
}
