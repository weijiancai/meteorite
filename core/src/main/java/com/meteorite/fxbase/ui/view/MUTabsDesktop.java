package com.meteorite.fxbase.ui.view;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.eventdata.LoaderEventData;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.search.MUSearchBox;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.event.FormFieldValueEvent;
import com.meteorite.fxbase.ui.meta.AddMetaGuide;
import com.meteorite.fxbase.ui.win.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.util.Callback;
import org.controlsfx.control.MasterDetailPane;

import java.sql.Connection;
import java.util.*;

/**
 * Tabs 桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane implements IDesktop {
    private MUSearchBox searchBox;
    protected MUTree tree;
    protected MUTabPane tabPane;
    protected Map<String, Tab> tabCache = new HashMap<String, Tab>();
    protected ITreeNode navTree;
    protected Label messageLabel;
    private Popup popup = new Popup();

    public MUTabsDesktop() {
        this(null);
    }

    public MUTabsDesktop(ITreeNode navTree) {
        this.navTree = navTree;
        /*TreeView<File> fileTree = new TreeView<>();
        fileTree.setRoot(new FileTreeItem(new File("/")));
        this.setRight(fileTree);*/
        tree = new MUTree(navTree);
    }

    public void initUI() {
        ToolBar toolBar = new ToolBar();
        searchBox = new MUSearchBox(this);
        tabPane = new MUTabPane();
        popup.getContent().add(searchBox);

        this.setTop(toolBar);
        final MasterDetailPane sp = new MasterDetailPane(Side.LEFT);
        sp.setDividerPosition(0.8);
        sp.setMasterNode(tabPane);
        sp.setDetailNode(tree);
        this.setCenter(sp);

        Tab tab = new Tab("桌面");
        tab.setClosable(false);
        tabPane.getTabs().add(tab);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        searchBox.setPrefWidth(200);

        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    ITreeNode node = tree.getSelected();
                    openTab(node);
                }
            }
        });

        tabPane.getTabs().addListener(new ListChangeListener<Tab>() {
            @Override
            public void onChanged(Change<? extends Tab> change) {
                if(change.next() && change.wasRemoved()) {
                    List<? extends Tab> removed = change.getRemoved();
                    if(removed.size() > 0) {
                        for (Tab tab : removed) {
                            tabCache.remove(tab.getId());
                        }
                    }
                }
            }
        });

        this.addEventHandler(KeyEvent.ANY, new MuEventHandler<KeyEvent>() {
            @Override
            public void doHandler(KeyEvent event) throws Exception {
                if (event.isControlDown() && event.getCode() == KeyCode.N) {
                    popup.centerOnScreen();
                    popup.show(BaseApp.getInstance().getStage());
                }
            }
        });
        tabPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                hideSearchBox();
            }
        });

        // 创建底部
        createBottom();

        initNavTree();
        initAfter();
    }

    @Override
    public void initAfter() {

    }

    private void initNavTree() {
        navTree = new BaseTreeNode("ROOT");
        final TreeItem<ITreeNode> navTreeItem = new TreeItem<ITreeNode>(navTree);
        navTreeItem.setExpanded(true);
        // 数据源管理
        BaseTreeNode dataSource = new BaseTreeNode("数据源管理");
        dataSource.setId("DataSource");
        dataSource.setView(View.createNodeView(new MUTable(MetaManager.getMeta("Datasource"))));
        final TreeItem<ITreeNode> dataSourceItem = new TreeItem<ITreeNode>(dataSource);
        // 数据字典
        BaseTreeNode dictNode = new BaseTreeNode("数据字典");
        dictNode.setId("Dict");
        dictNode.setView(View.createNodeView(new MUDictWin()));
        TreeItem<ITreeNode> dictItem = new TreeItem<ITreeNode>(dictNode);
        // 元数据
        BaseTreeNode metaNode = new BaseTreeNode("元数据");
        metaNode.setId("Meta");
        metaNode.setView(View.createNodeView(new MUMetaWin()));
        TreeItem<ITreeNode> metaItem = new TreeItem<ITreeNode>(metaNode);
        // 视图管理
        BaseTreeNode viewNode = new BaseTreeNode("视图管理");
        viewNode.setId("ViewManager");
        viewNode.setView(View.createNodeView(new MUViewWin()));
        TreeItem<ITreeNode> viewItem = new TreeItem<ITreeNode>(viewNode);
        // 项目管理
        MUProjectWin projectWin = new MUProjectWin();
        TreeItem<ITreeNode> projectItem = projectWin.getProjectTree();
        // 参数配置
        BaseTreeNode profileSetting = new BaseTreeNode("参数配置");
        profileSetting.setId("ProfileSetting");
        profileSetting.setView(View.createNodeView(new MUTable(MetaManager.getMeta("ProfileSetting"))));
        TreeItem<ITreeNode> ProfileSettingItem = new TreeItem<ITreeNode>(profileSetting);
        // 备份恢复
        MUBackupWin backupWin = new MUBackupWin();
        // Sql控制台
        MUSqlConsoleWin sqlConsoleWin = new MUSqlConsoleWin();

        tree.setRoot(navTreeItem);
        tree.setShowRoot(false);
        navTreeItem.getChildren().add(dictItem);
        navTreeItem.getChildren().add(metaItem);
        navTreeItem.getChildren().add(viewItem);
        navTreeItem.getChildren().add(projectItem);
        navTreeItem.getChildren().add(dataSourceItem);
        navTreeItem.getChildren().add(ProfileSettingItem);
        navTreeItem.getChildren().add(backupWin.getBackupTreeItem());
        navTreeItem.getChildren().add(sqlConsoleWin.getSqlConsoleTreeItem());

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
                    MUTreeItem item = new MUTreeItem(tree, node);
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

        // 启动服务
        service.start();
    }

    private void createBottom() {
        messageLabel = new Label("无消息");
        this.setBottom(messageLabel);
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
            String displayName = node.getPresentableText();
            if (UString.isEmpty(displayName)) {
                displayName = node.getDisplayName();
            }
            if (UString.isEmpty(displayName)) {
                displayName = node.getName();
            }
            Tab tab = tabCache.get(node.getId());
            if (tab == null) {
                tab = new Tab(displayName);
                tab.setId(node.getId());

                // 自定义视图
                Node nodeView = view.getNode();
                if (nodeView != null) {
                    tab.setContent(nodeView);
                } else {
                    // 底部数据库对象TabPane
                    MUTabPane dbObjTabPane = new MUTabPane();
                    dbObjTabPane.setSide(Side.BOTTOM);

                    Tab objDefTab = new Tab("对象定义");
                    objDefTab.setClosable(false);

                    Tab dataTab = new Tab("数据信息");
                    dataTab.setClosable(false);

                    Tab genCodeTab = getGenCodeTab(node);

                    dbObjTabPane.getTabs().addAll(objDefTab, dataTab, genCodeTab);
                    dbObjTabPane.getSelectionModel().select(dataTab);
                    MUTable table = new MUTable();
                    table.initUI(view.getMeta());
                    dataTab.setContent(table);

                    tab.setContent(dbObjTabPane);
                }

                tabPane.getTabs().add(tab);
                tabCache.put(node.getId(), tab);
            }
            tabPane.getSelectionModel().select(tab);
        }
    }

    public void hideSearchBox() {
        searchBox.reset();
        popup.hide();
    }

    private Tab getGenCodeTab(final ITreeNode node) {
        Meta meta = node.getView().getMeta();

        Tab tab = new Tab("生成代码");
        tab.setClosable(false);

        TabPane tabPane = new TabPane();

        for (Tab aTab : getGenCodeTabs(meta)) {
            tabPane.getTabs().addAll(aTab);
        }
        /*tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0) {
                    javaBeanTa.setText(meta.genJavaBeanCode());
                }
            }
        });*/
        tab.setContent(tabPane);

        return tab;
    }

    @Override
    public Parent getDesktop() {
        return this;
    }

    @Override
    public MUTree getNavTree() {
        return tree;
    }

    protected List<Tab> getGenCodeTabs(Meta meta) {
        // 生成JavaBean代码
        Tab javaBeanTab = new Tab("JavaBean");
        TextArea javaBeanTa = new TextArea();
        javaBeanTab.setContent(javaBeanTa);
        javaBeanTa.setText(meta.genJavaBeanCode());
        // 生成RowMapper代码
        Tab rowMapperTab = new Tab("RowMapper");
        TextArea rowMapperTa = new TextArea();
        rowMapperTab.setContent(rowMapperTa);
        rowMapperTa.setText(meta.genRowMapperCode());
        // 生成PDB代码
        Tab pdbTab = new Tab("PDB");
        TextArea pdbTa = new TextArea();
        pdbTab.setContent(pdbTa);
        pdbTa.setText(meta.getPDBCode());

        List<Tab> result = new ArrayList<Tab>();
        result.add(javaBeanTab);
        result.add(rowMapperTab);
        result.add(pdbTab);

        return result;
    }
}
