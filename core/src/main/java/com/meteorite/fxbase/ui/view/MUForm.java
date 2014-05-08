package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import com.meteorite.fxbase.ui.layout.MUFormLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MetaUI Form
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUForm extends BorderPane {
    private FormProperty formConfig;
    private MUFormLayout layout;
    private MUTable table;
    private BorderPane root;

    private Button prevButton;
    private Button nextButton;

    private TabPane tabPane;
    private DataMap data;
    private List<MUTable> children = new ArrayList<>();

    public MUForm(FormProperty property) {
        this(property, null);
    }

    public MUForm(FormProperty property, MUTable table) {
        this.formConfig = property;
        this.table = table;
        initUI();
    }

    private void initUI() {
        layout = new MUFormLayout(formConfig);

        if (FormType.QUERY == formConfig.getFormType() || FormType.READONLY == formConfig.getFormType()) {
            this.setCenter(layout);
        } else if (FormType.EDIT == formConfig.getFormType()) {
            root = new BorderPane();

            ToolBar controlBar = new ToolBar();
            controlBar.setStyle("-fx-padding: 5;");
            prevButton = new Button("前一条");
            nextButton = new Button("后一条");
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            Button btn_close = new Button("退出");
            btn_close.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setVisible(false);
                }
            });
            controlBar.getItems().addAll(prevButton, nextButton, region, btn_close);
            root.setTop(controlBar);

            tabPane = new TabPane();
            Tab mainTab = new Tab("主信息");
            mainTab.setClosable(false);
            mainTab.setContent(layout);
            tabPane.getTabs().add(mainTab);
            root.setCenter(tabPane);

            final Meta mainMeta = formConfig.getMeta();
            for (final Meta meta : mainMeta.getChildren()) {
                Tab tab = new Tab(meta.getDisplayName());
                final MUTable table = new MUTable(ViewManager.getViewByName(meta.getName() + "TableView"));
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
                                    builder.add(field.getColumn().getName(), data.get(field.getRefField().getColumn()), count < 1);
                                    count++;
                                }
                            }
                            try {
                                QueryResult<DataMap> queryResult = meta.query(builder);
                                table.getItems().setAll(queryResult.getRows());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                tabPane.getTabs().add(tab);
            }

            if (table != null) {
                registerEvent();
            }
            this.setCenter(root);
        }
    }

    public void setValues(DataMap result) {
        if (result == null) {
            return;
        }
        this.data = result;
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            entry.getValue().setValue(result.getString(entry.getKey()));
        }
    }

    public List<ICanQuery> getQueryList() {
        return layout.getQueryList();
    }

    public Map<String, IValue> getValueMap() {
        return layout.getValueMap();
    }

    private void registerEvent() {
        // 下一条按钮 注册事件
        nextButton.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent e) throws Exception {
                selectNext();
                initData();
            }
        });
        // 上一条按钮 注册事件
        prevButton.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent e) throws Exception {
                selectPrevious();
                initData();
            }
        });
    }

    private void selectNext() {
        table.getSelectionModel().clearSelection(table.getSelectionModel().getSelectedIndex());
        table.getSelectionModel().selectNext();
    }

    private void selectPrevious() {
        table.getSelectionModel().clearSelection(table.getSelectionModel().getSelectedIndex());
        table.getSelectionModel().selectPrevious();
    }

    public void initData() {
        // 选中主面板
        tabPane.getSelectionModel().selectFirst();
        // 初始化数据
        if (table == null) {
            return;
        }
        DataMap dataMap = table.getSelectionModel().getSelectedItem();
        if (dataMap == null) {
            return;
        }
        setValues(dataMap);

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

    public void reset() {
        tabPane.getSelectionModel().select(0);
        for (MUTable table : children) {
            table.getItems().clear();
        }
    }
}
