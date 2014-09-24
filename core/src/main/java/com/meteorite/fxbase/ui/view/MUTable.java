package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UList;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.group.GroupFunction;
import com.meteorite.core.util.group.GroupModel;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.form.MUListView;
import com.meteorite.fxbase.ui.component.guide.GuideModel;
import com.meteorite.fxbase.ui.component.table.cell.SortNumTableCell;
import com.meteorite.fxbase.ui.component.table.column.BaseTableColumn;
import com.meteorite.fxbase.ui.event.DataChangeEvent;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends BorderPane {
//    private View view;
//    private Meta meta;
    private TableProperty config;
    private TableView<DataMap> table = new TableView<DataMap>();
    private ToolBar toolBar;
    private boolean isShowToolBar = true;
    private boolean isEditable = false;
    private TableColumnChangeListener tableColumnChangeListener;

    public MUTable() {
    }

    public MUTable(View view) {
//        this.view = view;
//        this.meta = view.getMeta();
        initUI(view);
    }

    public void initUI(View view) {
        // 表格属性
        config = new TableProperty(view);
        initUI();
    }

    public void initUI(TableProperty tableProperty) {
        this.config = tableProperty;
        initUI();
    }

    private void initUI() {
//        tableView.setEditable(true);
        // 创建工具条
        if(isShowToolBar) {
            createToolbar();
        }
        // 创建表格列头信息
        createTableColumns();
        // 绑定数据
//        table.itemsProperty().bind(view.getMeta().dataListProperty());
        this.setCenter(table);

        if (isEditable) {
            table.setEditable(true);
        }
    }

    private void createTableColumns() {
        // 先删除监听
        if (tableColumnChangeListener != null) {
            table.getColumns().removeListener(tableColumnChangeListener);
        } else {
            tableColumnChangeListener = new TableColumnChangeListener();
        }
        // 删除所有列
        table.getColumns().remove(0, table.getColumns().size());
        // 创建序号列
        TableColumn<DataMap, String> sortNumCol = new TableColumn<DataMap, String>("序号");
        sortNumCol.setPrefWidth(50);
        sortNumCol.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
            @Override
            public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                return new SortNumTableCell();
            }
        });
        table.getColumns().add(sortNumCol);

        // 创建其他列
        for (TableFieldProperty property : config.getFieldProperties()) {
            table.getColumns().add(new BaseTableColumn(property));
        }
        // 列顺序改变
        table.getColumns().addListener(tableColumnChangeListener);
    }

    private void createToolbar() {
        if (toolBar != null) {
            return;
        }
        toolBar = new ToolBar();
        // 列信息
        Button btnCol = new Button("列信息");
        btnCol.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                View view = config.getView();
                if (view != null) {
                    BorderPane root = new BorderPane();
                    root.setPrefSize(800, 400);
                    TabPane tabPane = new TabPane();
                    String displayName = view.getMeta().getDisplayName();
                    if (UString.isEmpty(displayName)) {
                        displayName = view.getMeta().getName();
                    }
                    Tab main = new Tab(displayName);
                    main.setClosable(false);
                    main.setContent(getColInfoTable(ViewManager.getViewByName("TableFieldPropertyTableView"), config.getFieldProperties()));
                    tabPane.getTabs().addAll(main);
                    root.setCenter(tabPane);

                    Meta mainMeta = view.getMeta();
                    for (MetaReference ref : mainMeta.getReferences()) {
                        Meta pkMeta = ref.getPkMeta();
                        Tab tab = new Tab(pkMeta.getDisplayName());
                        tab.setClosable(false);
                        View pkTableView = pkMeta.getTableView();
                        TableProperty tableProperty = new TableProperty(pkTableView);
                        tab.setContent(getColInfoTable(ViewManager.getViewByName("TableFieldPropertyTableView"), tableProperty.getFieldProperties()));
                        tabPane.getTabs().addAll(tab);
                    }

                    MUDialog.showCustomDialog(null, "列信息", root, new Callback<Void, Void>() {
                        @Override
                        public Void call(Void param) {

                            return null;
                        }
                    });
                }
            }
        });
        toolBar.getItems().add(btnCol);

        // 分组
        Button btnGroup = new Button("分组");
        btnGroup.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                BorderPane borderPane = new BorderPane();
                final MUListView<TableFieldProperty> colNameList = new MUListView<TableFieldProperty>();
                colNameList.getItems().addAll(config.getFieldProperties());
                borderPane.setCenter(colNameList);

                VBox vbox = new VBox();
                final MUListView<TableFieldProperty> groupColList = new MUListView<TableFieldProperty>();
                groupColList.setMaxHeight(150);

                final MUTable table = new MUTable();
                table.setShowToolBar(false);
                table.setMaxHeight(300);
                table.setEditable(true);
                table.initUI(getGroupTableProperty());

                ToolBar toolBar = new ToolBar();
                Button btnAddGroupCol = new Button("添加分组列");
                btnAddGroupCol.setOnAction(new MuEventHandler<ActionEvent>() {
                    @Override
                    public void doHandler(ActionEvent event) throws Exception {
                        TableFieldProperty field = colNameList.getSelectionModel().getSelectedItem();
                        if (field == null) {
                            MUDialog.showInformation("请选择列！");
                            return;
                        }
                        // 删除列名
                        colNameList.getItems().remove(field);

                        groupColList.getItems().add(field);
                    }
                });
                Button btnAddComputeCol = new Button("添加统计列");
                btnAddComputeCol.setOnAction(new MuEventHandler<ActionEvent>() {
                    @Override
                    public void doHandler(ActionEvent event) throws Exception {
                        TableFieldProperty field = colNameList.getSelectionModel().getSelectedItem();
                        if (field == null) {
                            MUDialog.showInformation("请选择列！");
                            return;
                        }
                        // 删除列名
                        colNameList.getItems().remove(field);

                        DataMap dataMap = new DataMap();
                        dataMap.put("name", field.getName());
                        dataMap.put("colname", field.getDisplayName());
                        dataMap.put("groupfunction", "SUM");
                        dataMap.put("sourcecol", field);
                        table.getItems().add(dataMap);
                    }
                });
                Button btnDelete = new Button("删除");
                btnDelete.setOnAction(new MuEventHandler<ActionEvent>() {
                    @Override
                    public void doHandler(ActionEvent event) throws Exception {
                        TableFieldProperty field = groupColList.getSelectionModel().getSelectedItem();
                        if (field != null) {
                            groupColList.getItems().remove(field);
                            colNameList.getItems().add(field);
                        }

                        DataMap dataMap = table.getSelectionModel().getSelectedItem();
                        if (dataMap != null) {
                            table.getItems().remove(dataMap);
                            colNameList.getItems().add((TableFieldProperty) dataMap.get("sourcecol"));
                        }
                    }
                });
                toolBar.getItems().addAll(btnAddGroupCol, btnAddComputeCol, btnDelete);

                vbox.getChildren().addAll(groupColList, toolBar, table);
                borderPane.setRight(vbox);
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "分组设置", borderPane, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {
                        GroupModel model = new GroupModel();
                        String[] groupCols = new String[groupColList.getItems().size()];
                        int i = 0;
                        for (TableFieldProperty field : groupColList.getItems()) {
                            groupCols[i++] = field.getName();
                        }
                        model.setGroupCols(groupCols);

                        for (DataMap dataMap : table.getItems()) {
                            model.addComputeCol(dataMap.getString("name"), GroupFunction.getGroupFunction(dataMap.getString("groupfunction")));
                        }
                        List<DataMap> data = UList.group(table.getItems(), model);
                        TableProperty tableProperty = new TableProperty();
                        tableProperty.getFieldProperties().addAll(groupColList.getItems());
                        for (DataMap dataMap : table.getItems()) {
                            tableProperty.getFieldProperties().add((TableFieldProperty) dataMap.get("sourcecol"));
                        }
                        initUI(tableProperty);
                        getItems().addAll(data);
                        return null;
                    }
                });
            }
        });
        toolBar.getItems().add(btnGroup);

        Button btnAdd = new Button("增加");
        btnAdd.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
//                MUForm form = new MUForm()
            }
        });
        toolBar.getItems().add(btnAdd);

        this.setTop(toolBar);
    }


    public MUTable getColInfoTable(View view, List<TableFieldProperty> fieldProperties) {
        MUTable table = new MUTable(view);
        table.showToolbar(false);
        table.setEditable(true);
        List<DataMap> list = new ArrayList<DataMap>();
        for (final TableFieldProperty property : fieldProperties) {
            DataMap data = new DataMap();
            for (ViewProperty prop : view.getViewProperties()) {
                String id = prop.getProperty().getId();
                String name = prop.getProperty().getName();
                data.put(name, property.getPropertyValue(id));
            }
            list.add(data);
        }
        table.addEventHandler(DataChangeEvent.EVENT_TYPE, new MuEventHandler<DataChangeEvent>() {
            @Override
            public void doHandler(DataChangeEvent event) throws Exception {
                String fieldName = event.getRowData().getString("name");
                MetaField field = config.getView().getMeta().getFieldByName(fieldName.toLowerCase());
                ViewProperty viewProperty = config.getView().getViewProperty(field, event.getName());
                viewProperty.setValue(event.getNewValue());
                config.setPropertyValue(field, event.getName(), event.getNewValue());
                // 保存到数据库
                viewProperty.persist();
            }
        });
        table.getItems().addAll(list);

        return table;
    }

    private TableProperty getGroupTableProperty() {
        TableProperty tableProp = new TableProperty();
        List<TableFieldProperty> list = new ArrayList<TableFieldProperty>();
        list.add(new TableFieldProperty("name", "名称", null, false));
        list.add(new TableFieldProperty("colname", "列名", null, true));
        list.add(new TableFieldProperty("groupfunction", "分组函数", DictManager.getDict(GroupFunction.class), true));
        tableProp.getFieldProperties().addAll(list);
        return tableProp;
    }

    public TableProperty getConfig() {
        return config;
    }

    public View getView() {
        return config.getView();
    }

    public DataMap getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    public ObservableList<DataMap> getItems() {
        return table.getItems();
    }

    public TableSelectionModel<DataMap> getSelectionModel() {
        return table.getSelectionModel();
    }

    public TableView<DataMap> getSourceTable() {
        return table;
    }

    public void showToolbar(boolean isShow) {
        if (isShow) {
            setTop(toolBar);
        } else {
            setTop(null);
        }
    }

    public void setEditable(boolean editable) {
        this.isEditable = editable;
    }

    public boolean isShowToolBar() {
        return isShowToolBar;
    }

    public void setShowToolBar(boolean isShowToolBar) {
        this.isShowToolBar = isShowToolBar;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    class TableColumnChangeListener implements ListChangeListener<TableColumn<DataMap, ?>> {

        @Override
        public void onChanged(Change<? extends TableColumn<DataMap, ?>> change) {
            if (change.next() && !change.wasRemoved()) {
                Map<TableFieldProperty, Integer> map = new HashMap<TableFieldProperty, Integer>();
                for (int i = 0; i < config.getFieldProperties().size(); i++) {
                    TableFieldProperty property = config.getFieldProperties().get(i);
                    BaseTableColumn cur = (BaseTableColumn) change.getList().get(i + 1);
                    if (property.getSortNum() != cur.getProperty().getSortNum()) {
                        map.put(property, cur.getProperty().getSortNum());
                    }
                }
                // 更新序号
                for (Map.Entry<TableFieldProperty, Integer> entry : map.entrySet()) {
                    entry.getKey().setSortNum(entry.getValue());
                }
            }
        }
    }
}
