package com.meteorite.fxbase.ui.win;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBObjectImpl;
import com.meteorite.core.datasource.eventdata.LoaderEventData;
import com.meteorite.core.meta.MetaAdapter;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源管理窗口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class MUDataSourceWin extends BorderPane {
    private static final Logger log = Logger.getLogger(MUDataSourceWin.class);

    private TreeItem<ITreeNode> dataSourceItem;
    private Label messageLabel;
    private MUTree navTree;

    public MUDataSourceWin(Label messageLabel, MUTree tree) {
        this.messageLabel = messageLabel;
        this.navTree = tree;

        initUI();
    }

    private void initUI() {
        MUTable table = new MUTable(MetaManager.getMeta("Datasource"));

        BaseTreeNode dataSource = new BaseTreeNode("数据源管理");
        dataSource.setId("DataSource");
        dataSource.setView(View.createNodeView(table));

        dataSourceItem = new TreeItem<ITreeNode>(dataSource);
        // 添加数据源
        for (final DataSource ds : DataSourceManager.getDataSources()) {
            try {
                ITreeNode node = ds.getNavTree();
                MUTreeItem item = new MUTreeItem(node, false);
                dataSourceItem.getChildren().add(item);
            } catch (Exception e) {
                log.error("获得数据源导航树根节点失败！", e);
            }

        }


        // 导航树节点选中事件
        navTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ITreeNode>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<ITreeNode>> observable, TreeItem<ITreeNode> oldValue, final TreeItem<ITreeNode> newValue) {
                // 有子节点则不加载
                if (newValue == null || newValue.getChildren().size() > 0) {
                    return;
                }
                // 加载子节点
                ITreeNode node = newValue.getValue();
                if (node instanceof DBObjectImpl) {
                    final DBObjectImpl dbObject = (DBObjectImpl) node;
                    // 数据源不可用，直接返回
                    if (DBObjectType.DATABASE == dbObject.getObjectType() && !dbObject.getDataSource().isAvailable()) {
                        return;
                    }
                    /*List<ITreeNode> children = dbObject.getChildren();
                    for (ITreeNode treeNode : children) {
                        MUTreeItem item = new MUTreeItem(treeNode, false);
                        newValue.getChildren().add(item);
                    }*/

                    Service<List<ITreeNode>> service = new Service<List<ITreeNode>>() {
                        @Override
                        protected Task<List<ITreeNode>> createTask() {
                            return new Task<List<ITreeNode>>() {
                                @Override
                                protected List<ITreeNode> call() throws Exception {
                                    return dbObject.getChildren();
                                }
                            };
                        }
                    };
                    service.valueProperty().addListener(new ChangeListener<List<ITreeNode>>() {
                        @Override
                        public void changed(ObservableValue<? extends List<ITreeNode>> observable, List<ITreeNode> oldValue, List<ITreeNode> newNodes) {
                            for (ITreeNode treeNode : newNodes) {
                                MUTreeItem item = new MUTreeItem(treeNode, false);
                                newValue.getChildren().add(item);
                            }

                            // 展开父节点
                            newValue.setExpanded(true);
                            switch (dbObject.getObjectType()) {
                                case SCHEMAS:
                                case USERS:
                                case PRIVILEGES:
                                case CHARSETS:
                                case TABLES:
                                case VIEWS:
                                case INDEXES:
                                case TRIGGERS:
                                case PROCEDURES:
                                case FUNCTIONS:
                                case COLUMNS:
                                case CONSTRAINTS: {
                                    dbObject.setPresentableText("(" + newNodes.size() + ")");
                                }
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
            }
        });

        addDataSourceAddListener();
    }

    public TreeItem<ITreeNode> getDataSourceItem() {
        return dataSourceItem;
    }

    public void addDataSourceAddListener() {
        // 数据源，新增，后台执行作业，添加到缓存表中
        MetaManager.addMetaListener(new MetaAdapter() {
            @Override
            public void addEnd(Meta meta, DataMap rowData) {
                if ("Datasource".equals(meta.getName())) {
                    DataSourceType type = DataSourceType.valueOf(String.valueOf(rowData.get("type")));
                    if (DataSourceType.DATABASE == type) {
                        final DBDataSource ds = rowData.toClass(DBDataSource.class);
                        Service<List<BaseTreeNode>> service = new Service<List<BaseTreeNode>>() {
                            @Override
                            protected Task<List<BaseTreeNode>> createTask() {
                                return new Task<List<BaseTreeNode>>() {
                                    @Override
                                    protected List<BaseTreeNode> call() throws Exception {
                                        List<BaseTreeNode> result = new ArrayList<BaseTreeNode>();

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
                                MUDialog.showExceptionDialog(newValue);
                            }
                        });

                        // 启动服务
                        service.start();
                    }
                }
            }
        });
    }
}
