package com.meteorite.fxbase.ui.win;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.eventdata.LoaderEventData;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源管理窗口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class MUDataSourceWin extends BorderPane {
    private TreeItem<ITreeNode> dataSourceItem;
    private Label messageLabel;

    public MUDataSourceWin(Label messageLabel) {
        this.messageLabel = messageLabel;

        initUI();
    }

    private void initUI() {
        MUTable table = new MUTable(MetaManager.getMeta("Datasource"));

        BaseTreeNode dataSource = new BaseTreeNode("数据源管理");
        dataSource.setId("DataSource");
        dataSource.setView(View.createNodeView(table));

        dataSourceItem = new TreeItem<ITreeNode>(dataSource);

        Service<List<BaseTreeNode>> service = new Service<List<BaseTreeNode>>() {
            @Override
            protected Task<List<BaseTreeNode>> createTask() {
                return new Task<List<BaseTreeNode>>() {
                    @Override
                    protected List<BaseTreeNode> call() throws Exception {
                        List<BaseTreeNode> result = new ArrayList<BaseTreeNode>();
                        for (final DataSource ds : DataSourceManager.getDataSources()) {
                            ds.getLoaderSubject().registerObserver(new Observer<LoaderEventData>() {
                                @Override
                                public void update(LoaderEventData data) {
                                    updateMessage(data.getMessage());
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
                    MUTreeItem item = new MUTreeItem(node);
                    dataSourceItem.getChildren().add(item);
                }
            }
        });
        service.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                messageLabel.setText(newValue);
            }
        });
        service.exceptionProperty().addListener(new ChangeListener<Throwable>() {
            @Override
            public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue, Throwable newValue) {
                newValue.printStackTrace();
            }
        });

        // 启动服务
        service.start();
    }

    public TreeItem<ITreeNode> getDataSourceItem() {
        return dataSourceItem;
    }
}
