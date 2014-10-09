package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumDataStatus;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.observer.BaseSubject;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.CrudProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UList;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;
import com.meteorite.core.util.group.GroupFunction;
import com.meteorite.core.util.group.GroupModel;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.form.MUListView;
import com.meteorite.fxbase.ui.component.table.TableExportGuide;
import com.meteorite.fxbase.ui.component.table.cell.SortNumTableCell;
import com.meteorite.fxbase.ui.component.table.column.BaseTableColumn;
import com.meteorite.fxbase.ui.event.DataChangeEvent;
import com.meteorite.fxbase.ui.event.data.DataStatusEventData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.*;

/**
 * MetaUI Table
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTable extends StackPane {
    private TableProperty config;
    private ToolBar tableToolBar;
    private ToolBar formToolBar;
    private boolean isShowToolBar = true;
    private boolean isShowQueryForm = true;
    private boolean isEditable = false;
    private TableColumnChangeListener tableColumnChangeListener;

    private BorderPane tablePane = new BorderPane(); // 表格面板
    private BorderPane formPane = new BorderPane(); // 表单面板
    private TableView<DataMap> table = new TableView<DataMap>();
    private MUForm queryForm;
    private MUForm editForm;

    public static final String TOTAL_FORMAT = "总共%s条";
    private Pagination pagination;
    private Hyperlink totalLink;
    private TextField pageRowsTF;

    private TabPane formTabPane;
    private Button prevButton;
    private Button nextButton;
    private Set<MUTable> children = new HashSet<MUTable>();
    private Map<String, Button> tableButtonMap = new HashMap<String, Button>();

    private Subject<DataStatusEventData> dataStatusSubject = new BaseSubject<DataStatusEventData>();

    public MUTable() {
    }

    public MUTable(View view) {
        initUI(view);
    }

    public MUTable(Meta meta) {
        initUI(meta);
    }

    public void initUI(View view) {
        if (view.getName().endsWith("CrudView")) {
            initUI(view.getMeta());
        } else {
            // 表格属性
            config = new TableProperty(view);
            initUI();
        }
    }

    public void initUI(TableProperty tableProperty) {
        this.config = tableProperty;
        initUI();
    }

    public void initUI(Meta meta) {
        View crudView = ViewManager.getViewByName(meta.getName() + "CrudView");
        CrudProperty crudConfig = new CrudProperty(crudView);
        if (crudView != null) {
            queryForm = new MUForm(new FormProperty(crudConfig.getQueryView()));
            editForm = new MUForm(new FormProperty(crudConfig.getFormView()));
            editForm.setDataStatusSubject(dataStatusSubject);
            config = new TableProperty(crudConfig.getTableView());
        }
        initUI();
    }

    private void initUI() {
        // =============== 初始化表格面板 ===========================
        // 创建工具条
        if(isShowToolBar) {
            createTableToolbar();
        }
        // 添加查询条件form
        if (queryForm != null && isShowQueryForm) {
            VBox box = new VBox();
            box.getChildren().addAll(queryForm, tableToolBar);
            tablePane.setTop(box);
        } else {
            tablePane.setTop(tableToolBar);
        }
        // 创建表格列头信息
        createTableColumns();
        // 创建分页条
        createPagination();
        // 绑定数据
//        table.itemsProperty().bind(view.getMeta().dataListProperty());
        // 注册table行点击事件
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    openFormWin(table.getSelectionModel().getSelectedItem());
                }
            }
        });
        if (isEditable) {
            table.setEditable(true);
        }
        tablePane.setCenter(table);

        // =============== 初始化表单面板 ===========================
        formPane.setVisible(false);
        if (editForm != null) {
            // 创建表单工具条
            createFormToolbar();
            // 添加可编辑表单
            formPane.setCenter(editForm);
            // 创建表单Tabs
            createFormTabPane();
            // 添加数据状态监听
            dataStatusSubject.registerObserver(new Observer<DataStatusEventData>() {
                @Override
                public void update(DataStatusEventData data) {
                    if (data.getDataStatus() == EnumDataStatus.ADD_AFTER) { // 新增后在Table中新增一行
                        table.getItems().add(data.getForm().getData());
                    } else if (data.getDataStatus() == EnumDataStatus.UPDATE_END) { // 更新之后,更新行数据
                        getSelectedItem().putAll(data.getForm().getData());
                    }
                }
            });
        }

        // 添加到StackPane
        if (this.getChildren().size() == 0) {
            this.getChildren().addAll(formPane, tablePane);
        }

        // 注册按钮
        for (MUTable table : children) {
            Button button = tableButtonMap.get(table.getConfig().getMeta().getName());
            if (button != null) {
                table.getTableToolBar().getItems().add(button);
            }
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

    private void createTableToolbar() {
        if (tableToolBar != null) {
            return;
        }
        tableToolBar = new ToolBar();
        // 列信息
        Button btnCol = new Button("列信息");
        btnCol.setOnAction(new TableColumnEventHandler());
        // 分组
        Button btnGroup = new Button("分组");
        btnGroup.setOnAction(new TableColumnGroupEventHandler());
        // 增加
        Button btnAdd = new Button("增加");
        btnAdd.setOnAction(new FormAddEventHandler());
        // 删除
        Button btnDelete = new Button("删除");
        btnDelete.setOnAction(new TableDeleteEventHandler());
        // 查看
        Button btnLook = new Button("查看");
        btnLook.setOnAction(new TableLookEventHandler());
        // 导出数据
        Button btnExport = new Button("导出");
        btnExport.setOnAction(new TableExportEventHandler());
        // 复制数据
        Button btnCopy = new Button("复制");
        btnCopy.setOnAction(new TableRowCopyEventHandler());

        tableToolBar.getItems().addAll(btnCol, btnGroup, btnAdd, btnDelete, btnLook, btnExport, btnCopy);
        if (isShowQueryForm) {
            // 查询
            Button btnQuery = new Button("查询");
            btnQuery.setOnAction(new TableQueryEventHandler());
            tableToolBar.getItems().add(btnQuery);
        }
    }

    private void createFormToolbar() {
        if (formToolBar != null) {
            return;
        }
        formToolBar = new ToolBar();
        formToolBar.setStyle("-fx-padding: 5;");
        prevButton = new Button("前一条");
        prevButton.setOnAction(new FormPreviousRowEventHandler());
        nextButton = new Button("后一条");
        nextButton.setOnAction(new FormNextRowEventHandler());
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        Button btn_close = new Button("退出");
        btn_close.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                formPane.setVisible(false);
                tablePane.setVisible(true);
            }
        });

        // 新增
        Button btnAdd = new Button("新增");
        btnAdd.setOnAction(new FormAddEventHandler());
        // 保存
        Button btn_save = new Button("保存");
        btn_save.disableProperty().bind(editForm.isModifiedProperty().not());
        btn_save.setOnAction(new FormSaveEventHandler());
        formToolBar.getItems().addAll(prevButton, nextButton, region, btnAdd, btn_save, btn_close);
        formPane.setTop(formToolBar);
    }

    private void createPagination() {
        if (tablePane.getBottom() != null) {
            return;
        }
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);

        totalLink = new Hyperlink(String.format(TOTAL_FORMAT, 0));
        pagination = new Pagination(1);
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Meta meta = config.getMeta();
                try {
                    QueryResult<DataMap> queryResult = meta.query(queryForm.getQueryList(), newValue.intValue(), 15);
                    table.getItems().clear();
                    table.getItems().addAll(queryResult.getRows());
                    pagination.setPageCount(meta.getPageCount());
                    totalLink.setText(String.format(TOTAL_FORMAT, meta.getTotalRows()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Label pageRowsLabel = new Label("，每页显示");
        pageRowsTF = new TextField("15");
        box.getChildren().addAll(totalLink, pageRowsLabel, pageRowsTF, new Label("条"), pagination);

        tablePane.setBottom(box);
    }

    private void createFormTabPane() {
        formTabPane = new TabPane();
        Tab mainTab = new Tab("主信息");
        mainTab.setClosable(false);
        mainTab.setContent(editForm);
        formTabPane.getTabs().add(mainTab);
        formPane.setCenter(formTabPane);

        // Tabs
        final Meta mainMeta = config.getMeta();
        for (final Meta meta : mainMeta.getChildren()) {
            Tab tab = new Tab(meta.getDisplayName());
            final MUTable table = new MUTable();
            table.setShowQueryForm(false);
            table.initUI(meta);
            children.add(table);
            BorderPane pane = new BorderPane();
            pane.setCenter(table);
            tab.setContent(pane);
            tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        int count = 0;
                        QueryBuilder builder = QueryBuilder.create(meta);
                        for (MetaField field : meta.getFields()) {
                            if(field.getRefField() != null && field.getRefField().getMeta().equals(mainMeta)) {
                                builder.add(field.getOriginalName(), getSelectedItem().get(field.getRefField().getName()), count < 1);
                                count++;
                            }
                        }
                        try {
                            QueryResult<DataMap> queryResult = meta.query(builder);
                            table.getItems().setAll(queryResult.getRows());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            table.getSourceTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        FormProperty property = new FormProperty(ViewManager.getViewByName(meta.getName() + "FormView"));
                        property.setFormType(FormType.READONLY);
                        MUForm form = new MUForm(property);
                        form.setValues(table.getSelectedItem());
                        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
                    }
                }
            });
            formTabPane.getTabs().add(tab);
        }
    }

    public void initFormData() {
        // 选中主面板
        formTabPane.getSelectionModel().selectFirst();
        // 初始化数据
        DataMap dataMap = table.getSelectionModel().getSelectedItem();
        if (dataMap == null) {
            return;
        }
        editForm.setValues(dataMap);

        if (table.getItems().size() == 1) {
            nextButton.setDisable(true);
            prevButton.setDisable(true);

            return;
        }
        if (table.getSelectionModel().getSelectedIndex() == table.getItems().size() - 1) {
            nextButton.setDisable(true);
        } else {
            nextButton.setDisable(false);
        }
        if (table.getSelectionModel().getSelectedIndex() == 0) {
            prevButton.setDisable(true);
        } else {
            prevButton.setDisable(false);
        }
    }

    private void openFormWin(DataMap result) {
        formTabPane.getSelectionModel().select(0);
        for (MUTable table : children) {
            table.getItems().clear();
        }

        if (result == null) { // 新增
            editForm.add();
        } else { // 查看
            editForm.setValues(result);
        }
        formPane.setVisible(true);
        tablePane.setVisible(false);
//        MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "查看", form, null);
    }

    public void setMultiSelect(boolean isMulti) {
        table.getSelectionModel().setSelectionMode(isMulti ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
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

    public void setEditable(boolean editable) {
        this.isEditable = editable;
    }

    public void setShowQueryForm(boolean isShowQueryForm) {
        this.isShowQueryForm = isShowQueryForm;
    }

    public boolean isShowToolBar() {
        return isShowToolBar;
    }

    public void setShowToolBar(boolean isShowToolBar) {
        this.isShowToolBar = isShowToolBar;
    }

    public ToolBar getTableToolBar() {
        return tableToolBar;
    }

    public void addTableButton(String metaName, Button button) {
        tableButtonMap.put(metaName, button);
    }

    public Subject<DataStatusEventData> getDataStatusSubject() {
        return dataStatusSubject;
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

    class TableColumnEventHandler extends MuEventHandler<ActionEvent> {

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

        public MUTable getColInfoTable(View view, List<TableFieldProperty> fieldProperties) {
            MUTable table = new MUTable();
            table.setShowToolBar(false);
            table.setEditable(true);
            table.initUI(view);
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
    }

    class TableColumnGroupEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            BorderPane borderPane = new BorderPane();
            final MUListView<TableFieldProperty> colNameList = new MUListView<TableFieldProperty>();
            if (colNameList.getItems() != null) {
                colNameList.getItems().addAll(config.getFieldProperties());
            }
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

        private TableProperty getGroupTableProperty() {
            TableProperty tableProp = new TableProperty();
            List<TableFieldProperty> list = new ArrayList<TableFieldProperty>();
            list.add(new TableFieldProperty("name", "名称", null, false));
            list.add(new TableFieldProperty("colname", "列名", null, true));
            list.add(new TableFieldProperty("groupfunction", "分组函数", DictManager.getDict(GroupFunction.class), true));
            tableProp.getFieldProperties().addAll(list);
            return tableProp;
        }
    }

    class TableQueryEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            // 清空数据
            if (table.getItems() != null) {
                table.getItems().clear();
            }
            // 查询数据
            Meta meta = config.getMeta();
            QueryResult<DataMap> queryResult = meta.query(queryForm.getQueryList(), 0, UNumber.toInt(pageRowsTF.getText()));
            table.getItems().addAll(queryResult.getRows());
            pagination.setPageCount(meta.getPageCount());
            totalLink.setText(String.format(TOTAL_FORMAT, meta.getTotalRows()));
        }
    }

    class TableDeleteEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(ActionEvent event) throws Exception {
            Meta meta = config.getMeta();
            int selected = table.getSelectionModel().getSelectedIndex();
            meta.delete(selected);
            table.getItems().remove(selected);
        }
    }

    class TableLookEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            DataMap result = getSelectedItem();
            if (result == null) {
                MUDialog.showInformation("请选择数据行！");
                return;
            }
            openFormWin(result);
        }
    }

    class TableExportEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            TableExportGuide guide = new TableExportGuide(MUTable.this);
            MUDialog.showCustomDialog(null, "导出数据向导", guide, null);
        }
    }

    class TableRowCopyEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            final DataMap result = getSelectedItem();
            if (result == null) {
                MUDialog.showInformation("请选择数据行！");
                return;
            }
            final FormProperty formProperty = queryForm.getFormConfig();
            formProperty.setFormType(FormType.EDIT);

            // 主键值
            List<MetaField> fields = formProperty.getMeta().getPkFields();
            for (MetaField field : fields) {
                if ("GUID()".equals(field.getDefaultValue())) {
                    result.put(field.getName(), UUIDUtil.getUUID());
                }
            }

            final MUForm form = new MUForm(formProperty);
            form.setAdd(true);
            form.setValues(result);
            MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "复制", form, new Callback<Void, Void>() {
                @Override
                public Void call(Void param) {
                    try {
                        form.save();
                    } catch (Exception e) {
                        MUDialog.showExceptionDialog(e);
                    }
                    return null;
                }
            });
        }
    }

    class FormSaveEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(ActionEvent event) throws Exception {
            editForm.save();
        }
    }

    class FormNextRowEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            table.getSelectionModel().clearSelection(table.getSelectionModel().getSelectedIndex());
            table.getSelectionModel().selectNext();
            initFormData();
        }
    }

    class FormPreviousRowEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            table.getSelectionModel().clearSelection(table.getSelectionModel().getSelectedIndex());
            table.getSelectionModel().selectPrevious();
            initFormData();
        }
    }

    class FormAddEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            openFormWin(null);
        }
    }
}
