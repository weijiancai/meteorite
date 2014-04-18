package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.*;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 表单属性
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormProperty implements PropertyNames {
    public static final String NAME = "FORM.MP.name";
    public static final String DISPLAY_NAME = "FORM.MP.displayName";
    public static final String FORM_TYPE = "FORM.MP.formType";
    public static final String COL_COUNT = "FORM.MP.colCount";
    public static final String COL_WIDTH = "FORM.MP.colWidth";
    public static final String LABEL_GAP = "FORM.MP.labelGap";
    public static final String FIELD_GAP = "FORM.MP.fieldGap";
    public static final String HGAP = "FORM.MP.hgap";
    public static final String VGAP = "FORM.MP.vgap";

    private String name;
    private String displayName;
    private String formType;
    private int colCount;
    private int colWidth;
    private int labelGap;
    private int fieldGap;
    private int hgap;
    private int vgap;

    private List<FormFieldProperty> formFields = new ArrayList<FormFieldProperty>();

    public FormProperty(ViewLayout layout) {
        name = layout.getConfigValue(NAME);
        displayName = layout.getConfigValue(DISPLAY_NAME);
        formType = layout.getConfigValue(FORM_TYPE);
        colCount = UNumber.toInt(layout.getConfigValue(COL_COUNT));
        colWidth = UNumber.toInt(layout.getConfigValue(COL_WIDTH));
        labelGap = UNumber.toInt(layout.getConfigValue(LABEL_GAP));
        fieldGap = UNumber.toInt(layout.getConfigValue(FIELD_GAP));
        hgap = UNumber.toInt(layout.getConfigValue(HGAP));
        vgap = UNumber.toInt(layout.getConfigValue(VGAP));

        for (MetaField field : layout.getMeta().getFields()) {
            formFields.add(new FormFieldProperty(field, layout.getMetaFieldConfig(field.getId())));
        }
    }

    public FormProperty(View view, boolean isQuery) {
        if (isQuery) {
            name = view.getStringProperty(QUERY.NAME);
            displayName = view.getStringProperty(QUERY.DISPLAY_NAME);
            formType = view.getStringProperty(QUERY.FORM_TYPE);
            colCount = view.getIntProperty(QUERY.COL_COUNT);
            colWidth = view.getIntProperty(QUERY.COL_WIDTH);
            labelGap = view.getIntProperty(QUERY.LABEL_GAP);
            fieldGap = view.getIntProperty(QUERY.FIELD_GAP);
            hgap = view.getIntProperty(QUERY.HGAP);
            vgap = view.getIntProperty(QUERY.VGAP);

            for (MetaField field : view.getMetaFieldList()) {
                formFields.add(new FormFieldProperty(field, view.getMetaFieldConfig(field)));
            }
        } else {
            name = view.getStringProperty(FORM.NAME);
            displayName = view.getStringProperty(FORM.DISPLAY_NAME);
            formType = view.getStringProperty(FORM.FORM_TYPE);
            colCount = view.getIntProperty(FORM.COL_COUNT);
            colWidth = view.getIntProperty(FORM.COL_WIDTH);
            labelGap = view.getIntProperty(FORM.LABEL_GAP);
            fieldGap = view.getIntProperty(FORM.FIELD_GAP);
            hgap = view.getIntProperty(FORM.HGAP);
            vgap = view.getIntProperty(FORM.VGAP);

            for (MetaField field : view.getMetaFieldList()) {
                formFields.add(new FormFieldProperty(field, view.getMetaFieldConfig(field)));
            }
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

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
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

    public List<FormFieldProperty> getFormFields() {
        return formFields;
    }

    public static ViewLayout createViewLayout(Meta meta) {
        ViewLayout viewLayout = new ViewLayout();
        viewLayout.setId(UUIDUtil.getUUID());
        viewLayout.setMeta(meta);
        viewLayout.setLayout(LayoutManager.getLayoutByName("FORM"));

        List<ViewConfig> configList = new ArrayList<>();

        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(NAME), null, meta.getName() + "Form"));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(DISPLAY_NAME), null, meta.getDisplayName() + "表单"));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(FORM_TYPE), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(COL_COUNT), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(COL_WIDTH), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(LABEL_GAP), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(FIELD_GAP), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(HGAP), null, null));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(VGAP), null, null));

        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            configList.addAll(FormFieldProperty.getViewConfigs(viewLayout, field));
        }
        viewLayout.setConfigs(configList);

        return viewLayout;
    }

    public static View createFormView(Meta meta) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "FormView");
        view.setDisplayName(meta.getDisplayName() + "表单视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewProperty> viewProperties = new ArrayList<>();

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.NAME), meta.getName() + "Form"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.DISPLAY_NAME), meta.getDisplayName() + "表单"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.FORM_TYPE), null));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_COUNT), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_WIDTH), "180"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.LABEL_GAP), "5"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.FIELD_GAP), "15"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.HGAP), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.VGAP), "5"));



        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(FormFieldProperty.getViewProperties(view, field));
        }
        view.setViewProperties(viewProperties);

        return view;
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
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.DISPLAY_NAME), meta.getDisplayName() + "查询"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.FORM_TYPE), null));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.COL_COUNT), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.COL_WIDTH), "180"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.LABEL_GAP), "5"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.FIELD_GAP), "15"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.HGAP), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY.VGAP), "5"));



        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(FormFieldProperty.getQueryViewProperties(view, field));
        }
        view.setViewProperties(viewProperties);

        return view;
    }
}
