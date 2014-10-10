package com.meteorite.fxbase.ui.win;

import com.meteorite.core.codegen.CodeGen;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.dict.EnumDataStatus;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.tpl.CodeTpl;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.event.data.DataStatusEventData;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码模板窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUCodeTplWin extends BorderPane {
    private final ProjectDefine project;
    private MUTable tplTable;

    public MUCodeTplWin(ProjectDefine project) {
        this.project = project;

        initUI();
    }

    private void initUI() {
        final Meta meta = MetaManager.getMeta("CodeTpl");
        tplTable = new MUTable();
        tplTable.initUI(meta);
        tplTable.setMultiSelect(true);

        this.setCenter(tplTable);

        // 新增模板监听
        tplTable.getDataStatusSubject().registerObserver(new Observer<DataStatusEventData>() {
            @Override
            public void update(DataStatusEventData data) {
                if (data.getDataStatus() == EnumDataStatus.ADD_BEFORE) {
                    data.getForm().getNewRowData().put("projectId", project.getId());
                }
            }
        });

        // 添加按钮
        Button btnGenCode = new Button("生成代码");
        btnGenCode.setOnAction(new GenCodeEventHandler());
        tplTable.getTableToolBar().getItems().add(btnGenCode);

        // 检索数据
        QueryBuilder builder = QueryBuilder.create(meta);
        try {
            builder.add("project_id", project.getId());
            meta.query(builder);
        } catch (Exception e) {
            MUDialog.showExceptionDialog(e);
        }
    }

    class GenCodeEventHandler extends MuEventHandler<ActionEvent> {
        @Override
        public void doHandler(ActionEvent event) throws Exception {
            List<DataMap> tplList = tplTable.getSelectionModel().getSelectedItems();
            if (tplList == null || tplList.size() == 0) {
                MUDialog.showInformation("请选择模板！");
                return;
            }
            final List<CodeTpl> tpls = new ArrayList<CodeTpl>();
            for (DataMap dataMap : tplList) {
                tpls.add(dataMap.toClass(CodeTpl.class));
            }

            final MUTable table = new MUTable(MetaManager.getMeta("Meta"));
            table.setMultiSelect(true);
            MUDialog.showCustomDialog(MUCodeTplWin.this, "选择元数据", table, new Callback<Void, Void>() {
                @Override
                public Void call(Void param) {
                    List<DataMap> list = table.getSelectionModel().getSelectedItems();
                    if (list == null || list.size() == 0) {
                        MUDialog.showInformation("请选择元数据");
                        return null;
                    }

                    for (DataMap dataMap : list) {
                        Meta meta = MetaManager.getMetaById(dataMap.getString("id"));
                        new CodeGen(project, tpls, meta).gen();
                    }

                    MUDialog.showInformation("生成成功！");
                    return null;
                }
            });
        }
    }
}
