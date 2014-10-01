package com.meteorite.core.ui.layout.property;

import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.*;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UUIDUtil;

import java.util.*;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 表单属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormProperty implements PropertyNames {
    private String name;
    private String displayName;
    private FormType formType;
    private int colCount = 3;
    private int colWidth = 180;
    private int labelGap = 5;
    private int fieldGap = 15;
    private int hgap = 3;
    private int vgap = 5;
    private Meta meta;

    private List<FormFieldProperty> formFields = new ArrayList<FormFieldProperty>();

    public FormProperty() {
    }

    public FormProperty(ViewLayout layout) {
        name = layout.getConfigValue(FORM.NAME);
        displayName = layout.getConfigValue(FORM.DISPLAY_NAME);
//        formType = layout.getConfigValue(FORM.FORM_TYPE);
        colCount = UNumber.toInt(layout.getConfigValue(FORM.COL_COUNT));
        colWidth = UNumber.toInt(layout.getConfigValue(FORM.COL_WIDTH));
        labelGap = UNumber.toInt(layout.getConfigValue(FORM.LABEL_GAP));
        fieldGap = UNumber.toInt(layout.getConfigValue(FORM.FIELD_GAP));
        hgap = UNumber.toInt(layout.getConfigValue(FORM.HGAP));
        vgap = UNumber.toInt(layout.getConfigValue(FORM.VGAP));

        /*for (MetaField field : layout.getMeta().getFields()) {
            formFields.add(new FormFieldProperty(this, field, layout.getMetaFieldConfig(field.getId())));
        }*/
    }

    public FormProperty(View view) {
        this.meta = view.getMeta();
        name = view.getStringProperty(FORM.NAME);
        displayName = view.getStringProperty(FORM.DISPLAY_NAME);
        formType = FormType.convert(view.getStringProperty(FORM.FORM_TYPE));
        colCount = view.getIntProperty(FORM.COL_COUNT);
        colWidth = view.getIntProperty(FORM.COL_WIDTH);
        labelGap = view.getIntProperty(FORM.LABEL_GAP);
        fieldGap = view.getIntProperty(FORM.FIELD_GAP);
        hgap = view.getIntProperty(FORM.HGAP);
        vgap = view.getIntProperty(FORM.VGAP);

        for (MetaField field : view.getMetaFieldList()) {
            formFields.add(new FormFieldProperty(this, field, view.getMetaFieldConfig(field)));
        }
        // 排序
        Collections.sort(formFields, new Comparator<FormFieldProperty>() {
            @Override
            public int compare(FormFieldProperty o1, FormFieldProperty o2) {
                return o1.getSortNum() - o2.getSortNum();
            }
        });
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

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
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

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<FormFieldProperty> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldProperty> formFields) {
        this.formFields = formFields;
        for (FormFieldProperty property : formFields) {
            property.setFormProperty(this);
        }
    }

    public static View createFormView(Meta meta, boolean isQuery) {
        String name = meta.getName() + (isQuery ? "Query" : "Form");
        String displayName = meta.getDisplayName() + (isQuery ? "查询" :"表单");
        FormType formType = isQuery ? FormType.QUERY : FormType.EDIT;

        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(name + "View");
        view.setDisplayName(displayName + "视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewProperty> viewProperties = new ArrayList<ViewProperty>();

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.NAME), name));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.DISPLAY_NAME), displayName));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.FORM_TYPE), formType.name()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_COUNT), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_WIDTH), "180"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.LABEL_GAP), "5"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.FIELD_GAP), "15"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.HGAP), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.VGAP), "5"));

        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(FormFieldProperty.getViewProperties(view, field, formType));
        }
        view.setViewProperties(viewProperties);

        return view;
    }
}
