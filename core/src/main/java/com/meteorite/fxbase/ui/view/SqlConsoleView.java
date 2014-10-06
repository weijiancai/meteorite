package com.meteorite.fxbase.ui.view;

import javafx.geometry.Side;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;

/**
 * SQL控制台View
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlConsoleView extends BorderPane {
    private TextArea sqlTA;
    private MUTable table;

    public SqlConsoleView() {
        initUI();
    }

    private void initUI() {
        sqlTA = new TextArea();
        table = new MUTable();

        final MasterDetailPane sp = new MasterDetailPane(Side.LEFT);
        sp.setDividerPosition(0.8);
        sp.setMasterNode(sqlTA);
        sp.setDetailNode(table);
        this.setCenter(sp);
    }
}
