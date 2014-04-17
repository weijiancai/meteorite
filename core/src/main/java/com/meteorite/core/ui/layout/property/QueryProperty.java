package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 查询属性
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class QueryProperty implements PropertyNames {
    private String name;
    private String displayName;
    private int colCount;
    private int colWidth;
    private int labelGap;
    private int fieldGap;
    private int hgap;
    private int vgap;

    private List<QueryFieldProperty> formFields = new ArrayList<>();

    public QueryProperty(View view) {
        name = view.getStringProperty(FORM.NAME);
        displayName = view.getStringProperty(FORM.DISPLAY_NAME);
        colCount = view.getIntProperty(FORM.COL_COUNT);
        colWidth = view.getIntProperty(FORM.COL_WIDTH);
        labelGap = view.getIntProperty(FORM.LABEL_GAP);
        fieldGap = view.getIntProperty(FORM.FIELD_GAP);
        hgap = view.getIntProperty(FORM.HGAP);
        vgap = view.getIntProperty(FORM.VGAP);

        for (MetaField field : view.getMetaFieldList()) {
            formFields.add(new QueryFieldProperty(field, view.getMetaFieldConfig(field)));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public int getLabelGap() {
        return labelGap;
    }

    public void setLabelGap(int labelGap) {
        this.labelGap = labelGap;
    }

    public int getFieldGap() {
        return fieldGap;
    }

    public void setFieldGap(int fieldGap) {
        this.fieldGap = fieldGap;
    }

    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public int getVgap() {
        return vgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public List<QueryFieldProperty> getFormFields() {
        return formFields;
    }

    public static View createQueryView(Meta meta) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "QueryView");
        view.setDisplayName(meta.getDisplayName() + "查询视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewProperty> viewProperties = new ArrayList<>();

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.NAME), meta.getName() + "Query"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.DISPLAY_NAME), meta.getDisplayName() + "查询条件"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.COL_COUNT), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.COL_WIDTH), "180"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.LABEL_GAP), "5"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.FIELD_GAP), "15"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.HGAP), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.VGAP), "5"));

        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(QueryFieldProperty.getViewProperties(view, field));
        }
        view.setViewProperties(viewProperties);

        return view;
    }
}
