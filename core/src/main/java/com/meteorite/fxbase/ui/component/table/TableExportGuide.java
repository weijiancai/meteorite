package com.meteorite.fxbase.ui.component.table;

import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.fxbase.ui.component.guide.BaseGuide;
import com.meteorite.fxbase.ui.component.guide.GuideModel;
import com.meteorite.fxbase.ui.view.MUListView;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.scene.control.ListView;
import org.controlsfx.control.CheckListView;

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

        GuideModel selectColModel = new GuideModel();
        selectColModel.setTitle("选择列");

        List<String> cols = new ArrayList<>();
        for (TableFieldProperty field : table.getConfig().getFieldProperties()) {
            cols.add(field.getDisplayName());
        }
        MUListView<String> listView = new MUListView<>(cols);
        // 默认选择所有
        listView.selectAll();
        selectColModel.setContent(listView);

        modelList = new ArrayList<>();
        modelList.add(selectColModel);

        GuideModel model = new GuideModel();
        model.setTitle("选择文件类型");
        ListView<String> listView1 = new ListView<>();
        listView1.getItems().add("文本文件");
        model.setContent(listView1);
        modelList.add(model);
    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }
}
