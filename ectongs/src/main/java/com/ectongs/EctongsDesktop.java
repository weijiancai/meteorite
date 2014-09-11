package com.ectongs;

import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.impl.BaseNavTreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.view.MUTabPane;
import com.meteorite.fxbase.ui.view.MUTree;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;
import java.util.Map;

/**
 * 易诚通桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsDesktop extends BorderPane implements IDesktop {
    private MUTree tree;
    private TabPane tabPane;
    private Map<String, Tab> tabCache = new HashMap<String, Tab>();

    @Override
    public void initUI() {
        BaseTreeNode root = new BaseTreeNode("ROOT");
        BaseNavTreeNode dataSource = new BaseNavTreeNode("数据源");
        dataSource.setView(ViewManager.getViewByName("DatasourceCrudView"));
        root.getChildren().add(dataSource);

        tree = new MUTree(root);
        tabPane = new TabPane();

        this.setLeft(tree);
        this.setCenter(tabPane);

        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    INavTreeNode node = (INavTreeNode) tree.getSelected();
                    openTab(node);
                }
            }
        });
    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return tree;
    }
    public void openTab(INavTreeNode node) {
        if (node == null) {
            return;
        }

        // 展开数节点
        tree.expandTo(node);

        // 打开视图
        View view = node.getView();
        if (view != null) {
            String text = node.getName();
            Tab tab = tabCache.get(node.getId());
            if (tab == null) {
                tab = new Tab(text);
                tab.setId(node.getId());
                tab.setText(node.getDisplayName());
                tab.setContent(new MuCrud(view));

                tabPane.getTabs().add(tab);
                tabCache.put(node.getId(), tab);
            }
            tabPane.getSelectionModel().select(tab);
        }
    }
}
