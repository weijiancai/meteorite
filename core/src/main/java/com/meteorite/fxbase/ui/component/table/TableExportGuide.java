package com.meteorite.fxbase.ui.component.table;

import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.fxbase.ui.component.guide.BaseGuide;
import com.meteorite.fxbase.ui.component.guide.GuideModel;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格导出向导
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableExportGuide extends BaseGuide {
    private List<GuideModel> modelList;
    private MUTable table;

    public TableExportGuide(MUTable table) {
        this.table = table;

        init();
    }

    @Override
    public void initPrep() {
        super.initPrep();

        GuideModel model = new GuideModel();
        model.setTitle("选择列");
        ListView<String> listView = new ListView<>();
        List<String> cols = new ArrayList<>();
        for (TableFieldProperty field : table.getConfig().getFieldProperties()) {
            cols.add(field.getDisplayName());
        }
        listView.getItems().addAll(cols);
        model.setContent(listView);

        modelList = new ArrayList<>();
        modelList.add(model);
    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }
}
