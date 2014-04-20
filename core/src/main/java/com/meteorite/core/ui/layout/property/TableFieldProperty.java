package com.meteorite.core.ui.layout.property;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumAlign;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 表格字段属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableFieldProperty implements PropertyNames {
    private String name;
    private String displayName;
    private MetaDataType dataType;
    private int width;
    private boolean isDisplay;
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private EnumAlign align;
    private int sortNum;

    private DBColumn dbColumn;

    public TableFieldProperty(MetaField field, Map<String, String> propMap) {
        this.name = field.getName();
        this.displayName = field.getDisplayName();
        this.dataType = field.getDataType();
        this.width = UNumber.toInt(propMap.get(TABLE_FIELD.WIDTH));
        this.isDisplay = UString.toBoolean(propMap.get(TABLE_FIELD.IS_DISPLAY));
        this.displayStyle = DisplayStyle.getStyle(propMap.get(TABLE_FIELD.DISPLAY_STYLE));
        this.dict = DictManager.getDict(propMap.get(TABLE_FIELD.DICT_ID));
        this.align = EnumAlign.getAlign(propMap.get(TABLE_FIELD.ALIGN));
        this.dbColumn = field.getColumn();
        this.sortNum = UNumber.toInt(propMap.get(TABLE_FIELD.SORT_NUM));
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

    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
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

    public DBColumn getDbColumn() {
        return dbColumn;
    }

    public EnumAlign getAlign() {
        return align;
    }

    public void setAlign(EnumAlign align) {
        this.align = align;
    }

    public void setDbColumn(DBColumn dbColumn) {
        this.dbColumn = dbColumn;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field) {
        List<ViewProperty> viewProperties = new ArrayList<>();
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.NAME), field, field.getName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.IS_DISPLAY), field, "true"));

        DBColumn column = field.getColumn();
        int w = column.getMaxLength();
        if(w > 500) {
            w = 200;
        }
        if ((column.isPk() || column.isFk()) && w == 32) {
            w = 250;
        }
        String width = w + "";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String align = EnumAlign.LEFT.name();
        String dictId = "";
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            width = "50";
            displayStyle = DisplayStyle.BOOLEAN.name();
        } else if (MetaDataType.INTEGER == field.getDataType()) {
            width = "60";
            align = EnumAlign.CENTER.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            width = "140";
        }

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.WIDTH), field, width));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_STYLE), field, displayStyle));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.ALIGN), field, align));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DICT_ID), field, dictId));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return viewProperties;
    }
}
