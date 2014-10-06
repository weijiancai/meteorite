package com.meteorite.fxbase.ui.component.guide;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.fxbase.ui.ICanInput;
import com.meteorite.fxbase.ui.component.form.MUCheckListView;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 元数据创建向导
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaCreateGuide extends BaseGuide {
    private List<GuideModel> modelList;
    private MUCheckListView<VirtualResource> tableList;

    public MetaCreateGuide() {
        modelList = new ArrayList<GuideModel>();
        modelList.add(new DataSourceSelectModel());

        init();
    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }

    @Override
    public void doFinish(DataMap param) throws Exception {
        List<VirtualResource> selectedResources = tableList.getSelectionModel().getSelectedItems();
        if (selectedResources == null || selectedResources.size() == 0) {
            MUDialog.showInformation("请选择表");
            return;
        }

        JdbcTemplate template = new JdbcTemplate();
        try {
            for (VirtualResource resource : selectedResources) {
                // 创建元数据
                Meta meta = MetaManager.initMetaFromResource(template, resource);
                // 创建视图
                ViewManager.createViews(meta, template);
            }

            template.commit();
        } finally {
            template.close();
        }

        MUDialog.showInformation("创建成功！");
    }

    /**
     * 选择数据源向导模型
     */
    class DataSourceSelectModel extends GuideModel {
        private BorderPane root = new BorderPane();

        public DataSourceSelectModel() {
            this.setTitle("选择数据源");
            this.setContent(root);
            this.initUI();
        }

        private void initUI() {
            // 数据源
            HBox dsHbox = new HBox();
            dsHbox.setAlignment(Pos.CENTER_LEFT);
            dsHbox.setStyle("-fx-padding: 5px");
            dsHbox.setSpacing(15);
            ChoiceBox<DataSource> dsChoice = new ChoiceBox<DataSource>();
            dsChoice.setPrefWidth(150);
            dsChoice.getItems().addAll(DataSourceManager.getDataSources());
            dsHbox.getChildren().addAll(new Label("数据源"), dsChoice);
            // 表、视图
            tableList = new MUCheckListView<VirtualResource>();
            tableList.setName("tables");
            tableList.getListView().setCellFactory(new Callback<ListView<VirtualResource>, ListCell<VirtualResource>>() {
                @Override
                public ListCell<VirtualResource> call(ListView<VirtualResource> param) {
                    return new CheckBoxListCell<VirtualResource>(new Callback<VirtualResource, ObservableValue<Boolean>>() {
                        @Override
                        public ObservableValue<Boolean> call(VirtualResource param) {
                            return tableList.getListView().getItemBooleanProperty(param);
                        }
                    }) {
                        @Override
                        public void updateItem(VirtualResource item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getName() + " - " + item.getDisplayName());
                            }
                        }
                    };
                }
            });
            // 数据源选择事件监听
            dsChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DataSource>() {
                @Override
                public void changed(ObservableValue<? extends DataSource> observable, DataSource oldValue, DataSource newValue) {
                    if (newValue.getType() == DataSourceType.DATABASE) {
                        List<VirtualResource> list = newValue.findResourcesByPath("/tables");
                        list.addAll(newValue.findResourcesByPath("/views"));
                        tableList.setItems(list);
                    }
                }
            });

            root.setTop(dsHbox);
            root.setCenter(tableList);
        }

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public void doOpen() {

        }

        @Override
        public void doNext() {

        }

        @Override
        public ICanInput getInputControl() {
            return tableList;
        }
    }
}
