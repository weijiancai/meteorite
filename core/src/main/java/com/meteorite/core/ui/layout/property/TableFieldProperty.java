package com.meteorite.core.ui.layout.property;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import java.util.Map;

/**
 * 表格字段属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableFieldProperty {
    private String name;
    private String displayName;
    private MetaDataType dataType;
    private int width;
    private boolean isDisplay;
    private DisplayStyle displayStyle;
    private DictCategory dict;

    private DBColumn dbColumn;

    public TableFieldProperty(MetaField field, Map<String, String> propMap) {
        this.name = field.getName();
        this.displayName = field.getDisplayName();
        this.dataType = field.getDataType();
        this.width = UNumber.toInt(propMap.get("width"));
        this.isDisplay = UString.toBoolean(propMap.get("isDisplay"));
        this.displayStyle = DisplayStyle.getStyle(propMap.get("displayStyle"));
        this.dict = DictManager.getDict(propMap.get("dictId"));
        this.dbColumn = field.getColumn();
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

    public void setDbColumn(DBColumn dbColumn) {
        this.dbColumn = dbColumn;
    }
}
