package com.meteorite.fxbase.ui.component.table;

import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.fxbase.ui.ValueConverter;
import com.meteorite.fxbase.ui.component.form.MUCheckListView;
import com.meteorite.fxbase.ui.component.form.MUListView;
import com.meteorite.fxbase.ui.component.guide.BaseGuide;
import com.meteorite.fxbase.ui.component.guide.GuideModel;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        List<TableFieldProperty> cols = new ArrayList<>();
        for (TableFieldProperty field : table.getConfig().getFieldProperties()) {
            cols.add(field);
        }
        MUCheckListView<TableFieldProperty> listView = new MUCheckListView<>(cols);
        listView.setName("colNames");
        listView.setValueConvert(new ValueConverter<TableFieldProperty>() {
            @Override
            public String toString(TableFieldProperty field) {
                return field.getName();
            }

            @Override
            public TableFieldProperty fromString(String string) {
                return null;
            }
        });
        // 默认选择所有
        listView.selectAll();
        selectColModel.setContent(listView);

        modelList = new ArrayList<>();
        modelList.add(selectColModel);

        GuideModel model = new GuideModel();
        model.setTitle("选择文件类型");
        MUListView<String> listView1 = new MUListView<>();
        listView1.setName("fileType");
        listView1.getItems().add("文本文件");
        model.setContent(listView1);
        modelList.add(model);
    }

    @Override
    public List<GuideModel> getModelList() {
        return modelList;
    }

    @Override
    public void doFinish(Map<String, String> param) throws FileNotFoundException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存文件");
        String fileType = param.get("fileType");
        if ("文本文件".equals(fileType)) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("文本文件", "*.txt"));
        }
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            table.getView().getMeta().toTxtFile(file, param);
        }
    }
}
