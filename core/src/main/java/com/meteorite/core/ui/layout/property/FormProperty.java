package com.meteorite.core.ui.layout.property;

import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
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
        name = view.getStringProperty(FORM.NAME, meta.getName());
        displayName = view.getStringProperty(FORM.DISPLAY_NAME, meta.getDisplayName());
        FormType defaultFormType = FormType.EDIT;
        if (view.getName().endsWith("QueryView")) {
            defaultFormType = FormType.QUERY;
        }
        formType = FormType.convert(view.getStringProperty(FORM.FORM_TYPE, defaultFormType.name()));
        colCount = view.getIntProperty(FORM.COL_COUNT, 3);
        colWidth = view.getIntProperty(FORM.COL_WIDTH, 180);
        labelGap = view.getIntProperty(FORM.LABEL_GAP, 5);
        fieldGap = view.getIntProperty(FORM.FIELD_GAP, 15);
        hgap = view.getIntProperty(FORM.HGAP, 3);
        vgap = view.getIntProperty(FORM.VGAP, 5);

        for (MetaField field : meta.getFields()) {
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
        /*viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_COUNT), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.COL_WIDTH), "180"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.LABEL_GAP), "5"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.FIELD_GAP), "15"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.HGAP), "3"));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM.VGAP), "5"));*/

        // 创建属性配置
        for (MetaField field : meta.getFields()) {
            viewProperties.addAll(FormFieldProperty.getViewProperties(view, field, formType));
        }
        view.setViewProperties(viewProperties);

        return view;
    }

    public String toHtml(boolean isTable) {
        StringBuilder sb = new StringBuilder();

        int rowIdx = 0;
        int colIdx = 0;

        String wrapperTag = "table";
        String rowTag = "tr";
        String colTag = "td";
        if (!isTable) {
            wrapperTag = "ul";
            rowTag = "li";
            colTag = "span";
        }

        sb.append(String.format("<%s class=\"mu-form\"><%s>", wrapperTag, rowTag));
        for (FormFieldProperty field : getFormFields()) {
            if (!field.isDisplay()) {
                continue;
            }

            if (field.isSingleLine()) {
                rowIdx++;
                sb.append(String.format("</%s><%1$s>", rowTag));
                sb.append(String.format("<%s class=\"field_label\"><label class=\"control-label\">%s</label></%1$s>", colTag, field.getDisplayName()));
                sb.append(String.format("<%s  class=\"field_input\" colspan=\"%d\">", colTag, getColCount() * 2 - 1)).append(getInputControl(field, true)).append(String.format("</%s>", colTag));
                sb.append(String.format("</%s><%1$s>", rowTag));
                colIdx = 0;
                rowIdx++;
                continue;
            }

            sb.append(String.format("<%s class=\"field_label\"><label class=\"control-label\">%s</label></%1$s>", colTag, field.getDisplayName()));
            sb.append(String.format("<%s class=\"field_input\">", colTag)).append(getInputControl(field, false)).append(String.format("</%s>", colTag));

            if (getColCount() == 1) {
                colIdx = 0;
                rowIdx++;
                sb.append(String.format("</%s><%1$s>", rowTag));
            } else {
                if (colIdx == getColCount() - 1) {
                    colIdx = 0;
                    rowIdx++;
                    sb.append(String.format("</%s><%1$s>", rowTag));
                } else {
                    colIdx++;
                }
            }
        }

        sb.append(String.format("</%s></%s>", rowTag, wrapperTag));
        return sb.toString().replace("<tr></tr>", "");
    }

    public String getInputControl(FormFieldProperty field, boolean isSingleLine) {
        StringBuilder sb = new StringBuilder();

        String colName = field.getName();
        if (field.getDisplayStyle() == DisplayStyle.COMBO_BOX || field.getDisplayStyle() == DisplayStyle.BOOLEAN) {
            sb.append(String.format("<select class=\"dictList\" dictId=\"%s\" name=\"%s\" style=\"width:150px;\" %s></select>",
                    field.getDict().getId(), colName));
        } else if(field.getDisplayStyle() == DisplayStyle.DATE) {
            if (field.getFormProperty().getFormType() == FormType.QUERY) {
                sb.append("<div class=\"controls\">\n" +
                        "    <div class=\"form-date-range btn\"><i class=\"icon-calendar\"></i>&nbsp;<span></span><b class=\"caret\"></b></div>\n" +
                        "</div>");
            } else { // 日期
                sb.append("<div class=\"form-group input-append date mu_date\" data-date-format=\"yyyy-mm-dd\">\n" +
                        "    <input class=\"form-control\" type=\"text\" name=\"" + field.getName() + "\" readonly>\n" +
                        "    <span class=\"add-on\"><i class=\"icon-th\"></i></span>\n" +
                        "</div> ");
            }
        }
        else {
            if (isSingleLine) {
                sb.append(String.format("<input type=\"text\" name=\"%s\" class=\"m-wrap\" style=\"width:100%%;\">", colName));
            } else {
                sb.append(String.format("<input name=\"%s\"  type=\"text\" class=\"m-wrap\">", colName));
            }
        }

        return sb.toString();
    }

    public FormFieldProperty getFormField(String name) {
        for (FormFieldProperty fieldProperty : formFields) {
            if (fieldProperty.getName().equals(name)) {
                return fieldProperty;
            }
        }
        return null;
    }
}
