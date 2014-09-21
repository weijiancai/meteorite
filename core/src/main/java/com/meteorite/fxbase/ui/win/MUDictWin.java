package com.meteorite.fxbase.ui.win;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.DictTreeNode;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;

/**
 * 数据字典窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUDictWin extends BorderPane {
    private MUTable table;
    private Meta dzCodeMeta;

    public MUDictWin() {
        initUI();
    }

    private void initUI() {
        dzCodeMeta = MetaManager.getMeta("Code");

        MasterDetailPane sp = new MasterDetailPane(Side.LEFT);
        sp.setDividerPosition(0.2);
        sp.setMasterNode(createCenter());
        sp.setDetailNode(createLeft());

        this.setCenter(sp);
    }

    private MUTree createLeft() {
        final MUTree tree = new MUTree(new DictTreeNode(DictManager.getRoot()));
        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    ITreeNode node = tree.getSelected();
                    String id = node.getId();
                    QueryBuilder builder = QueryBuilder.create(dzCodeMeta);
                    builder.add("category_id", id);
                    QueryResult<DataMap> queryResult = dzCodeMeta.query(builder);
                    table.getItems().addAll(queryResult.getRows());
                }
            }
        });
        return tree;
    }

    private Node createCenter() {
        table = new MUTable();
        table.initUI(ViewManager.getViewByName("CodeTableView"));
        return table;
    }
}
