package com.ectongs.view;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.dict.*;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

/**
 * DS Config 配置视图
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DsConfigView extends BorderPane {
    public DsConfigView() {
        initUI();
    }

    private void initUI() {
        TabPane tabPane = new TabPane();
        List<DataSource> dataSources = DataSourceManager.getDataSources();
        for (DataSource ds : dataSources) {
            if (!ds.isAvailable()) {
                continue;
            }
            VirtualResource vr = ds.findResourceByPath("/table/bs_sys_ds_datastore");
            if (vr != null) {
                Tab tab = new Tab(ds.getDisplayName());
                MuCrud crud = new MuCrud();
                Meta meta = MetaManager.getMetaByRsId(vr.getId());
                crud.initUI(getDsConfigQueryFormProperty(meta), getDsConfigEditFormProperty(meta), getDsConfigTableProperty(meta));
                tab.setContent(crud);

                tabPane.getTabs().add(tab);
            }
        }
//        View crudView = ViewManager.getViewByName("");
        this.setCenter(tabPane);
    }

    public FormProperty getDsConfigQueryFormProperty(Meta meta) {
        FormProperty form = new FormProperty();
        form.setColCount(3);
        form.setFormType(FormType.QUERY);
        form.setMeta(meta);

        List<FormFieldProperty> fields = new ArrayList<FormFieldProperty>();
        fields.add(new FormFieldProperty("dsId", "DS编号", QueryModel.LIKE, 10));
        fields.add(new FormFieldProperty("dsName", "名称", QueryModel.LIKE, 20));
        fields.add(new FormFieldProperty("updatetable", "更新表", QueryModel.LIKE, 30));

        form.setFormFields(fields);
        return form;
    }

    public FormProperty getDsConfigEditFormProperty(Meta meta) {
        FormProperty form = new FormProperty();
        form.setColCount(3);
        form.setFormType(FormType.EDIT);
        form.setMeta(meta);

        List<FormFieldProperty> fields = new ArrayList<FormFieldProperty>();
        fields.add(new FormFieldProperty("dsId", "DS编号", QueryModel.LIKE, 10));
        fields.add(new FormFieldProperty("dsName", "名称", QueryModel.LIKE, 20));
        fields.add(new FormFieldProperty("updatetable", "更新表", QueryModel.LIKE, 30));

        form.setFormFields(fields);
        return form;
    }

    public TableProperty getDsConfigTableProperty(Meta meta) {
        DictCategory booleanDict = DictManager.getDict(EnumBoolean.class);

        List<TableFieldProperty> fields = new ArrayList<TableFieldProperty>();
        fields.add(new TableFieldProperty("dsId", "DS编号", 130));
        fields.add(new TableFieldProperty("dsName", "名称", 150));
        fields.add(new TableFieldProperty("dsUserType", "用户分类", 100, null, EnumAlign.CENTER));
        fields.add(new TableFieldProperty("dbi", "数据源", 80, null, EnumAlign.CENTER));
        fields.add(new TableFieldProperty("updateAble", "参与更新", 65, booleanDict));
        fields.add(new TableFieldProperty("isExtend", "扩展DSCONF", 78, booleanDict));
        fields.add(new TableFieldProperty("receiptServer", "单据服务", 137, null, EnumAlign.CENTER));
        fields.add(new TableFieldProperty("canAdd", "可新增", 37, booleanDict));
        fields.add(new TableFieldProperty("canDelete", "可删除", 37, booleanDict));
        fields.add(new TableFieldProperty("inputDate", "录入时间", 130));
        fields.add(new TableFieldProperty("lastModDate", "修改时间", 130));
        fields.add(new TableFieldProperty("updatetable", "更新表", 180));

        TableProperty table = new TableProperty();
        table.setMeta(meta);

        table.setFieldProperties(fields);

        return table;
    }
}
