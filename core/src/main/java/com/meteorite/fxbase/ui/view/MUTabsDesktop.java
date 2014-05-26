package com.meteorite.fxbase.ui.view;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IDesktop;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.search.MUSearchBox;
import com.meteorite.fxbase.ui.event.FormFieldValueEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.controlsfx.control.MasterDetailPane;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabs 桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTabsDesktop extends BorderPane implements IDesktop {
    private ToolBar toolBar;
    private MUSearchBox searchBox;
    private MUTree tree;
    private MUTabPane tabPane;
    private MUTabPane dbObjTabPane;
    private Map<String, Tab> tabCache = new HashMap<>();
    private INavTreeNode navTree;
    private Popup popup = new Popup();

    public MUTabsDesktop(INavTreeNode navTree) {
        this.navTree = navTree;
        /*TreeView<File> fileTree = new TreeView<>();
        fileTree.setRoot(new FileTreeItem(new File("/")));
        this.setRight(fileTree);*/
    }

    public void initUI() {
        toolBar = new ToolBar();
        searchBox = new MUSearchBox(this);
        tree = new MUTree(navTree);
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

        Button btnAddDs = new Button("添加数据源");
        btnAddDs.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                FormProperty formProperty = new FormProperty(ViewManager.getViewByName("DBDataSourceFormView"));
                formProperty.setFormType(FormType.READONLY);
                final MUForm form = new MUForm(formProperty);
                form.addEventHandler(FormFieldValueEvent.EVENT_TYPE, new MuEventHandler<FormFieldValueEvent>() {
                    @Override
                    public void doHandler(FormFieldValueEvent event) throws Exception {
                        if ("DatabaseType".equalsIgnoreCase(event.getName())) {
                            DatabaseType type = DatabaseType.valueOf(event.getNewValue());
                            switch (type) {
                                case MYSQL:
                                    form.setValue("DriverClass", "com.mysql.jdbc.Driver");
                                    form.setValue("Url", "jdbc:mysql://localhost:3306/");
                                    break;
                                case SQLSERVER:
                                    form.setValue("DriverClass", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                                    form.setValue("Url", "jdbc:sqlserver://192.168.0.109:1433");
                                    break;
                            }
                        }
                    }
                });
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "新增数据源", form, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {
                        Map<String, IValue> map = form.getValueMap();
                        System.out.println(map);
                        String name = map.get("Name").value();
                        DBDataSource ds = new DBDataSource();
                        ds.setName(name);
                        ds.setDatabaseType(DatabaseType.get(map.get("DatabaseType").value()));
                        ds.setDriverClass(map.get("DriverClass").value());
                        ds.setUrl(map.get("Url").value());
                        ds.setUsername(map.get("Username").value());
                        ds.setPassword(map.get("Password").value());

                        if (UString.isEmpty(name)) {
                            return null;
                        }

                        try {
                            Connection connection = ds.getDbConnection().getConnection();
                            if (connection == null) {
                                MUDialog.showInformation("数据源配置错误！");
                                return null;
                            }
                            ConnectionUtil.closeConnection(connection);
                            ProjectConfig projectConfig = BaseApp.getInstance().getFacade().getProjectConfig();
                            projectConfig.getDataSources().add(ds);
                            SystemManager.save(projectConfig);
                            TreeItem<ITreeNode> item = tree.addTreeNode(ds.getNavTree());
                            tree.getRoot().getChildren().add(item);
                        } catch (Exception e) {
                            MUDialog.showExceptionDialog(e);
                        }
                        return null;
                    }
                });
            }
        });
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        searchBox.setPrefWidth(200);
        toolBar.getItems().addAll(btnAddDs);

        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    INavTreeNode node = (INavTreeNode) tree.getSelected();
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

                // 底部数据库对象TabPane
                MUTabPane dbObjTabPane = new MUTabPane();
                dbObjTabPane.setSide(Side.BOTTOM);
                Tab objDefTab = new Tab("对象定义");
                objDefTab.setClosable(false);
                Tab dataTab = new Tab("数据信息");
                dataTab.setClosable(false);
                dbObjTabPane.getTabs().addAll(objDefTab, dataTab);
                dbObjTabPane.getSelectionModel().select(dataTab);
                dataTab.setContent(new MuCrud(view));

                tab.setContent(dbObjTabPane);
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

    @Override
    public Parent getDesktop() {
        return this;
    }
}
