package com.meteorite.fxbase;

import com.meteorite.core.meta.MetaManager;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxTestDesktop extends BorderPane implements IDesktop {

    @Override
    public void initUI() {
        MUTable table = new MUTable();
//        table.initUI(ViewManager.getViewByName("CategoryTableView"));
        table.initUI(MetaManager.getMeta("Category"));
        this.setCenter(table);
    }

    @Override
    public void initAfter() {

    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return null;
    }
}
