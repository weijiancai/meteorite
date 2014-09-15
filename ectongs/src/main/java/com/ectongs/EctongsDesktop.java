package com.ectongs;

import com.meteorite.core.ITree;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.view.MUTabsDesktop;
import com.meteorite.fxbase.ui.view.MUTree;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 易诚通桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsDesktop extends MUTabsDesktop {
    private static final Logger log = Logger.getLogger(EctongsDesktop.class);


    public EctongsDesktop() {

    }

    @Override
    public void initAfter() {
        super.initAfter();
        tree.setShowRoot(true);
        navTree = new BaseTreeNode("ROOT");
        final MUTreeItem navTreeItem = new MUTreeItem(tree, navTree);

        final BaseTreeNode dataSource = new BaseTreeNode("数据源");
        dataSource.setView(ViewManager.getViewByName("DatasourceCrudView"));
        final MUTreeItem dataSourceItem = new MUTreeItem(tree, dataSource);

        navTreeItem.getChildren().add(dataSourceItem);
        tree.setRoot(navTreeItem);



        Service<List<BaseTreeNode>> service = new Service<List<BaseTreeNode>>() {
            @Override
            protected Task<List<BaseTreeNode>> createTask() {
                return new Task<List<BaseTreeNode>>() {
                    @Override
                    protected List<BaseTreeNode> call() throws Exception {
                        List<BaseTreeNode> result = new ArrayList<BaseTreeNode>();
                        for (final DataSource ds : DataSourceManager.getDataSources()) {
                            ds.registerObserver(new Observer() {
                                @Override
                                public void update(String message) {
                                    updateMessage(message);
                                }
                            });
                            ds.load();
                            BaseTreeNode dsNode = new BaseTreeNode(ds.getDisplayName());
                            for (ITreeNode node : ds.getNavTree().getChildren()) {
                                dsNode.getChildren().add(node);
                            }
                            result.add(dsNode);
                        }
                        return result;
                    }
                };
            }
        };
        service.valueProperty().addListener(new ChangeListener<List<BaseTreeNode>>() {
            @Override
            public void changed(ObservableValue<? extends List<BaseTreeNode>> observable, List<BaseTreeNode> oldValue, List<BaseTreeNode> newValue) {
                for (BaseTreeNode node : newValue) {
                    MUTreeItem item = new MUTreeItem(tree, node);
                    navTreeItem.getChildren().add(item);
                    tree.buildTree(node, item);
                }
                dataSourceItem.setExpanded(true);
            }
        });
        service.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                messageLabel.setText(newValue);
            }
        });

        navTree.getChildren().add(dataSource);

        navTreeItem.getChildren().add(new MUTreeItem(tree, new BaseTreeNode("Test")));
        // 启动服务
        service.start();
    }

    public void openTab(ITreeNode node) {
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
                String displayName = node.getDisplayName();
                if (UString.isEmpty(displayName)) {
                    displayName = node.getName();
                }
                tab.setText(displayName);
                tab.setContent(new MuCrud(view));

                tabPane.getTabs().add(tab);
                tabCache.put(node.getId(), tab);
            }
            tabPane.getSelectionModel().select(tab);
        }
    }
}
