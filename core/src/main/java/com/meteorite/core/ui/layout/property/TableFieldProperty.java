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
import com.meteorite.core.ui.model.LayoutProperty;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.ui.model.ViewLayout;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ViewManager.createViewConfig;

/**
 * 表格字段属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TableFieldProperty {
    public static final String NAME = "TABLE.IP.name";
    public static final String DISPLAY_NAME = "TABLE.IP.displayName";
    public static final String IS_DISPLAY = "TABLE.IP.isDisplay";
    public static final String WIDTH = "TABLE.IP.width";
    public static final String DISPLAY_STYLE = "TABLE.IP.displayStyle";
    public static final String ALIGN = "TABLE.IP.align";
    public static final String DICT_ID = "TABLE.IP.dictId";
    public static final String SORT_NUM = "TABLE.IP.sortNum";

    private String name;
    private String displayName;
    private MetaDataType dataType;
    private int width;
    private boolean isDisplay;
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private EnumAlign align;

    private DBColumn dbColumn;

    public TableFieldProperty(MetaField field, Map<String, String> propMap) {
        this.name = field.getName();
        this.displayName = field.getDisplayName();
        this.dataType = field.getDataType();
        this.width = UNumber.toInt(propMap.get("width"));
        this.isDisplay = UString.toBoolean(propMap.get("isDisplay"));
        this.displayStyle = DisplayStyle.getStyle(propMap.get("displayStyle"));
        this.dict = DictManager.getDict(propMap.get("dictId"));
        this.align = EnumAlign.getAlign(propMap.get("align"));
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

    public EnumAlign getAlign() {
        return align;
    }

    public void setAlign(EnumAlign align) {
        this.align = align;
    }

    public void setDbColumn(DBColumn dbColumn) {
        this.dbColumn = dbColumn;
    }

    public static List<ViewConfig> getViewConfigs(ViewLayout viewLayout, MetaField field) {
        List<ViewConfig> configList = new ArrayList<>();
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(NAME), field, field.getName()));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(DISPLAY_NAME), field, field.getDisplayName()));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(IS_DISPLAY), field, "true"));

        String width = "80";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String align = EnumAlign.LEFT.name();
        String dictId = "";
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            width = "60";
            displayStyle = DisplayStyle.BOOLEAN.name();
        } else if (MetaDataType.INTEGER == field.getDataType()) {
            width = "60";
            align = EnumAlign.CENTER.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            width = "120";
        }

        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(WIDTH), field, width));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(DISPLAY_STYLE), field, displayStyle));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(ALIGN), field, align));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(DICT_ID), field, dictId));
        configList.add(createViewConfig(viewLayout, LayoutManager.getLayoutPropByName(SORT_NUM), field, field.getSortNum() + ""));

        return configList;
    }
}
