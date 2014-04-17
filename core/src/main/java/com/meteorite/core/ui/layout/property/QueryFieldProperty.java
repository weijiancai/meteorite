package com.meteorite.core.ui.layout.property;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 查询字段属性
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class QueryFieldProperty implements PropertyNames {
    private String name;
    private String displayName;
    private String colName;
    private QueryModel queryModel;
    private boolean isDisplay;
    private int width;
    private int height;
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private MetaDataType dataType;
    private String value;
    private int sortNum;

    public QueryFieldProperty(MetaField field, Map<String, String> propMap) {
        dataType = field.getDataType();
        colName = field.getColumn().getName();
        name = propMap.get("name");
        displayName = propMap.get("displayName");
        queryModel = QueryModel.valueOf(propMap.get("queryModel"));
        isDisplay = UString.toBoolean(propMap.get("isDisplay"));
        width = UNumber.toInt(propMap.get("width"));
        displayStyle = DisplayStyle.getStyle(propMap.get("displayStyle"));
        dict = DictManager.getDict(propMap.get("dictId"));
        value = propMap.get("value");
        sortNum = UNumber.toInt(propMap.get("sortNum"));
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

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field) {
        List<ViewProperty> configList = new ArrayList<>();
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.NAME), field, field.getName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.COL_NAME), field, field.getColumn().getFullName()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.IS_DISPLAY), field, "true"));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.QUERY_MODEL), field, QueryModel.EQUAL.name()));

        String width = "180";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String dictId = field.getDictId();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            displayStyle = DisplayStyle.BOOLEAN.name();
            dictId = "EnumBoolean";
        } else if (MetaDataType.DICT == field.getDataType()) {
            displayStyle = DisplayStyle.COMBO_BOX.name();
        }

        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.WIDTH), field, width));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.DISPLAY_STYLE), field, displayStyle));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.VALUE), field, field.getDefaultValue()));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.DICT_ID), field, dictId));
        configList.add(new ViewProperty(view, LayoutManager.getLayoutPropById(QUERY_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return configList;
    }
}
