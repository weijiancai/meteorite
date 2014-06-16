package com.meteorite.fxbase.ui.meta;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
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

        GuideModel selectDbModel = new GuideModel();
        selectDbModel.setTitle("选择数据源");
        MUListView<DataSource> dbListView = new MUListView<>();
        dbListView.getItems().addAll(DataSourceManager.getDataSources());
        selectDbModel.setContent(dbListView);

        /*GuideModel selectTableModel = new GuideModel();
        selectTableModel.setTitle("选择表");
        MUCheckListView<String> tableListView = new MUCheckListView<>();*/

        modelList = new ArrayList<>();
        modelList.add(selectDbModel);

    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }


    @Override
    public void doFinish(DataMap param) throws FileNotFoundException, SQLException {

    }
}
