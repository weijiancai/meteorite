package com.meteorite.fxbase.ui.meta;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.fxbase.ui.component.form.MUCheckListView;
import com.meteorite.fxbase.ui.component.form.MUListView;
import com.meteorite.fxbase.ui.component.guide.BaseGuide;
import com.meteorite.fxbase.ui.component.guide.GuideModel;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加元数据向导
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class AddMetaGuide extends BaseGuide {
    private List<GuideModel> modelList;

    public AddMetaGuide() {
        init();
    }

    @Override
    public void initPrep() {
        super.initPrep();

        GuideModel selectDbModel = new DbSelectModel("选择数据源");
        selectDbModel.setGuide(this);
        GuideModel selectTableModel = new TableSelectModel("选择表");
        selectTableModel.setGuide(this);

        modelList = new ArrayList<GuideModel>();
        modelList.add(selectDbModel);
        modelList.add(selectTableModel);
    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }


    @Override
    public void doFinish(DataMap param) throws FileNotFoundException, SQLException {

    }

    /**
     * 选择数据库GuideModel
     */
    class DbSelectModel extends GuideModel {
        MUListView<DBDataSource> dbListView = new MUListView<DBDataSource>();

        public DbSelectModel(String title) {
            this.setTitle(title);
            dbListView.getItems().addAll(DataSourceManager.getAvailableDbDataSource());
            this.setContent(dbListView);
        }

        @Override
        public boolean isOk() {
            return dbListView.getSelectionModel().getSelectedIndex() >= 0;
        }

        @Override
        public void doOpen() {

        }

        @Override
        public void doNext() {
            getGuide().getDataMap().put("dbds", dbListView.getSelectionModel().getSelectedItem());
        }
    }

    class TableSelectModel extends GuideModel {
        private MUCheckListView<DBTable> tableListView;

        public TableSelectModel(String title) {
            this.setTitle(title);

            tableListView = new MUCheckListView<DBTable>();
            this.setContent(tableListView);
        }

        @Override
        public boolean isOk() {
            return !tableListView.getSelectionModel().isEmpty();
        }

        @Override
        public void doOpen() {
            DBDataSource ds = (DBDataSource) getGuide().getDataMap().get("dbds");
            List<DBTable> result = new ArrayList<DBTable>();
            try {
                for (DBSchema schema : ds.getSchemas()) {
                    result.addAll(schema.getTables());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableListView.setItems(result);
        }

        @Override
        public void doNext() {
        }
    }
}
