package com.meteorite.fxbase.ui.win;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.DictTreeNode;
import com.meteorite.core.dict.EnumDataStatus;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.event.data.DataStatusEventData;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUForm;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.MasterDetailPane;

import java.util.Map;

/**
 * 树形主明细窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class MUTreeTableWin extends BorderPane {
    private MUTree tree;
    private MUTable table;
    private Meta itemMeta;
    private Meta mainMeta;
    private MUForm mainForm;

    private TabPane tabPane = new TabPane();

    public MUTreeTableWin() {

    }

    public void initUI() {
        mainMeta = getMainMeta();
        itemMeta = getItemMeta();

        createLeft();
        createCenter();

        MasterDetailPane sp = new MasterDetailPane(Side.LEFT);
        sp.setDividerPosition(0.2);
        sp.setMasterNode(tabPane);
        sp.setDetailNode(tree);

        this.setCenter(sp);
    }

    private void createLeft() {
        tree = new MUTree(getRootTreeNode());
        tree.setShowRoot(true);
        tree.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                ITreeNode node = tree.getSelected();
                // 虚拟节点不处理
                if (node == null || node.isVirtual()) {
                    return;
                }
                // 加载明细信息
                if (itemMeta != null) {
                    String id = node.getId();
                    QueryBuilder builder = QueryBuilder.create(itemMeta);
                    builder.add(getItemFkDbName(), id);
                    QueryResult<DataMap> queryResult = itemMeta.query(builder);
                    table.getItems().clear();
                    table.getItems().addAll(queryResult.getRows());
                }

                // 加载主信息
                DataMap data = mainMeta.queryByPK(node.getId());
                mainForm.setValues(data);
            }
        });
    }

    private void createCenter() {
        // 主Tab
        Tab mainTab = new Tab(mainMeta.getDisplayName());
        mainTab.setClosable(false);
        FormProperty mainFormProp = new FormProperty(ViewManager.getFormView(mainMeta));
        mainFormProp.setFormType(FormType.EDIT);
        mainForm = new MUForm(mainFormProp);

        // 控制条
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-padding: 10");
        hBox.setSpacing(20);
        // 增加按钮
        Button btnAdd = new Button("增加");
        btnAdd.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                ITreeNode node = tree.getSelected();
                if (node == null) {
                    MUDialog.showInformation("请选择父节点！");
                    return;
                }
                DataMap data = new DataMap();
                data.put(getParentIdColName(), node.getId());
                mainForm.add(data);
            }
        });
        // 保存按钮
        Button btnSave = new Button("保存");
        btnSave.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                mainForm.save();
                BaseTreeNode parent = (BaseTreeNode) tree.getSelected();
                ITreeNode child = createNewTreeNode(mainForm.getValueMap());
                child.setParent(parent);
                if (parent.getChildren().size() == 0) {
                    parent.getChildren().add(child);
//                    tree.setRoot(new MUTreeItem(tree, getRootTreeNode()));
                    tree.buildTree(parent.getParent(), tree.getTreeItem(parent));
                    tree.expandTo(child);
                } else {
                    tree.getTreeItem(parent).getChildren().add(new MUTreeItem(tree, child));
                }
            }
        });
        // 删除按钮
        Button btnDel = new Button("删除");
        btnDel.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                ITreeNode node = tree.getSelected();
                if (node == null) {
                    MUDialog.showInformation("请选择要删除的节点！");
                    return;
                }

                if (tree.getSelectionModel() != null) {
                    TreeItem<ITreeNode> item = tree.getSelectionModel().getSelectedItem();
                    if (item.getChildren().size() > 0) {
                        MUDialog.showInformation("该节点下有子节点不能删除！");
                        return;
                    }
                    mainMeta.deleteByPK(node.getId());
                    if (item.getParent() != null) {
                        item.getParent().getChildren().remove(item);
                    }
                }
                mainForm.reset();
            }
        });
        btnSave.disableProperty().bind(mainForm.isModifiedProperty().not());
        hBox.getChildren().addAll(btnAdd, btnSave, btnDel);
        VBox box = new VBox();
        box.getChildren().addAll(mainForm, hBox);
        mainTab.setContent(box);
        tabPane.getTabs().add(mainTab);

        if (itemMeta != null) {
            // 明细Tab
            Tab itemTab = new Tab(itemMeta.getDisplayName());
            itemTab.setClosable(false);
            table = new MUTable();
            table.setShowQueryForm(false);
            table.initUI(itemMeta);
            itemTab.setContent(table);
            // 添加明细新增数据监听
            table.getDataStatusSubject().registerObserver(new Observer<DataStatusEventData>() {
                @Override
                public void update(DataStatusEventData data) {
                    ITreeNode node = tree.getSelected();
                    if (node == null) {
                        MUDialog.showInformation("请选择父节点！");
                        return;
                    }
                    if (data.getDataStatus() == EnumDataStatus.ADD_BEFORE) {
                        data.getForm().getNewRowData().put(getItemFkColName(), node.getId());
                    }
                }
            });
            tabPane.getTabs().add(itemTab);
        }
    }

    public void setSelectItem(int selectIdx) {
        tabPane.getSelectionModel().select(selectIdx);
    }

    public abstract ITreeNode getRootTreeNode();

    public abstract Meta getMainMeta();

    public abstract Meta getItemMeta();

    public abstract String getParentIdColName();

    public abstract String getItemFkColName();

    public abstract String getItemFkDbName();

    public abstract ITreeNode createNewTreeNode(Map<String, IValue> valueMap);
}
