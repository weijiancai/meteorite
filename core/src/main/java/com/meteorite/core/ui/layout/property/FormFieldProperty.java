package com.meteorite.core.ui.layout.property;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.*;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldProperty implements PropertyNames {
    private String name;
    private String columnName;
    private String displayName;
    private QueryModel queryModel;
    private boolean isSingleLine;
    private boolean isDisplay;
    private int width;
    private int height;
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private MetaDataType dataType;
    private String value;
    private int sortNum;

    private FormProperty formProperty;
    private MetaField metaField;

    public FormFieldProperty(FormProperty formProperty, MetaField field, Map<String, String> propMap) {
        this.formProperty = formProperty;
        this.metaField = field;

        dataType = field.getDataType();
        name = propMap.get(FORM_FIELD.NAME);
        if (field.getColumn() != null) {
            columnName = field.getColumn().getName();
        } else {
            columnName = name;
        }
        displayName = propMap.get(FORM_FIELD.DISPLAY_NAME);
        this.queryModel = QueryModel.convert(propMap.get(FORM_FIELD.QUERY_MODEL));
        isSingleLine = UString.toBoolean(propMap.get(FORM_FIELD.IS_SINGLE_LINE));
        isDisplay = UString.toBoolean(propMap.get(FORM_FIELD.IS_DISPLAY));
        width = UNumber.toInt(propMap.get(FORM_FIELD.WIDTH));
        height = UNumber.toInt(propMap.get(FORM_FIELD.HEIGHT));
        displayStyle = DisplayStyle.getStyle(propMap.get(FORM_FIELD.DISPLAY_STYLE));
        dict = DictManager.getDict(propMap.get(FORM_FIELD.DICT_ID));
        value = propMap.get(FORM_FIELD.VALUE);
        sortNum = UNumber.toInt(propMap.get(FORM_FIELD.SORT_NUM));
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
    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }
    public boolean isSingleLine() {
        return isSingleLine;
    }

    public void setSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    public DictCategory getDict() {
        return dict;
    }

    public void setDict(DictCategory dict) {
        this.dict = dict;
    }

    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FormProperty getFormProperty() {
        return formProperty;
    }

    public void setFormProperty(FormProperty formProperty) {
        this.formProperty = formProperty;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field, FormType formType) {
        List<ViewProperty> configList = new ArrayList<>();
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.NAME), field, field.getName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.IS_DISPLAY), field, "true"));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.IS_SINGLE_LINE), field, "false"));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.QUERY_MODEL), field, QueryModel.EQUAL.name()));

        String width = "180";
        String height = "";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String dictId = field.getDictId();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            displayStyle = DisplayStyle.BOOLEAN.name();
            dictId = "EnumBoolean";
        } else if (MetaDataType.DICT == field.getDataType()) {
            displayStyle = DisplayStyle.COMBO_BOX.name();
        } else if (MetaDataType.DATE == field.getDataType() && FormType.QUERY == formType) {
            width = "250";
        }

        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.WIDTH), field, width));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.HEIGHT), field, height));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DISPLAY_STYLE), field, displayStyle));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.VALUE), field, field.getDefaultValue()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.DICT_ID), field, dictId));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(FORM_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return configList;
    }

    public MetaField getMetaField() {
        return metaField;
    }
}
