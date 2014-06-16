package com.meteorite.core.ui.layout.property;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumAlign;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表格字段属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
@MetaElement(displayName = "表格字段配置")
public class TableFieldProperty extends BaseProperty {
    private String name;
    private StringProperty displayName = new SimpleStringProperty();
    private MetaDataType dataType;
    private IntegerProperty width = new SimpleIntegerProperty();
    private BooleanProperty isDisplay = new SimpleBooleanProperty(true);
    private DisplayStyle displayStyle;
    private DictCategory dict;
    private ObjectProperty<EnumAlign> align = new SimpleObjectProperty<>();
    private IntegerProperty sortNum = new SimpleIntegerProperty(0);

    public TableFieldProperty(MetaField field, Map<String, ViewProperty> propMap) {
        super(field, propMap);
        this.name = field.getName();
        setDisplayName(field.getDisplayName());
        this.dataType = field.getDataType();
        setWidth(UNumber.toInt(getPropertyValue(TABLE_FIELD.WIDTH)));
        setDisplay(UString.toBoolean(getPropertyValue(TABLE_FIELD.IS_DISPLAY)));
        this.displayStyle = DisplayStyle.getStyle(getPropertyValue(TABLE_FIELD.DISPLAY_STYLE));
        this.dict = DictManager.getDict(getPropertyValue(TABLE_FIELD.DICT_ID));
        setAlign(EnumAlign.getAlign(getPropertyValue(TABLE_FIELD.ALIGN)));
        setSortNum(UNumber.toInt(getPropertyValue(TABLE_FIELD.SORT_NUM)));

        // 属性改变，保存到数据库
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue.intValue() != newValue.intValue()) {
                    ViewProperty viewProperty = getProperty(TABLE_FIELD.WIDTH);
                    try {
                        viewProperty.setValue(newValue.toString());
                        viewProperty.persist();
                    } catch (Exception e) {
                        MUDialog.showExceptionDialog(e);
                    }
                }
            }
        });
        sortNumProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue.intValue() != newValue.intValue()) {
                    ViewProperty viewProperty = getProperty(TABLE_FIELD.SORT_NUM);
                    try {
                        viewProperty.setValue(newValue.toString());
                        viewProperty.persist();
                    } catch (Exception e) {
                        MUDialog.showExceptionDialog(e);
                    }
                }
            }
        });
    }

    @MetaFieldElement(displayName = "名称", sortNum = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "显示名", sortNum = 20)
    public String getDisplayName() {
        return displayName.get();
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    @MetaFieldElement(displayName = "数据类型", sortNum = 30)
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    @MetaFieldElement(displayName = "宽", sortNum = 40, dataType = MetaDataType.INTEGER)
    public int getWidth() {
        return width.get();
    }

    public void setWidth(int width) {
        this.width.set(width);
    }

    @MetaFieldElement(displayName = "是否显示", sortNum = 50, dataType = MetaDataType.BOOLEAN)
    public boolean isDisplay() {
        return isDisplay.get();
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay.set(isDisplay);
    }

    @MetaFieldElement(displayName = "显示风格", sortNum = 60, dictId = "DisplayStyle", dataType = MetaDataType.DICT)
    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    @MetaFieldElement(displayName = "数据字典", sortNum = 70, dictId = "ROOT", dataType = MetaDataType.DICT)
    public DictCategory getDict() {
        return dict;
    }

    public void setDict(DictCategory dict) {
        this.dict = dict;
    }

    @MetaFieldElement(displayName = "对齐方式", sortNum = 80, dictId = "EnumAlign", dataType = MetaDataType.DICT)
    public EnumAlign getAlign() {
        return align.get();
    }

    public void setAlign(EnumAlign align) {
        this.align.set(align);
    }

    @MetaFieldElement(displayName = "排序号", sortNum = 90, dataType = MetaDataType.INTEGER)
    public int getSortNum() {
        return sortNum.get();
    }

    public void setSortNum(int sortNum) {
        this.sortNum.set(sortNum);
    }

    public IntegerProperty widthProperty() {
        return width;
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public ObjectProperty<EnumAlign> alignProperty() {
        return align;
    }

    public BooleanProperty displayProperty() {
        return isDisplay;
    }

    public IntegerProperty sortNumProperty() {
        return sortNum;
    }

    public static List<ViewProperty> getViewProperties(View view, MetaField field, boolean isDisplay) {
        List<ViewProperty> viewProperties = new ArrayList<>();
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.NAME), field, field.getName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_NAME), field, field.getDisplayName()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.IS_DISPLAY), field, isDisplay ? "true" : "false"));

        DBColumn column = field.getColumn();
        int w = 80;
        if (column != null) {
            w = column.getMaxLength();
            if ((column.isPk() || column.isFk()) && w == 32) {
                w = 250;
            }
        }
        if(w > 500) {
            w = 200;
        }

        String width = w + "";
        String displayStyle = DisplayStyle.TEXT_FIELD.name();
        String align = EnumAlign.LEFT.name();
        String dictId = field.getDictId();
        if (MetaDataType.BOOLEAN == field.getDataType()) {
            width = "50";
            displayStyle = DisplayStyle.BOOLEAN.name();
        } else if (MetaDataType.INTEGER == field.getDataType()) {
            width = "60";
            align = EnumAlign.CENTER.name();
        } else if (MetaDataType.DATE == field.getDataType()) {
            width = "140";
        }
        if (UString.isNotEmpty(dictId)) {
            displayStyle = DisplayStyle.COMBO_BOX.name();
        }

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.WIDTH), field, width));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DISPLAY_STYLE), field, displayStyle));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.ALIGN), field, align));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.DICT_ID), field, dictId));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(TABLE_FIELD.SORT_NUM), field, field.getSortNum() + ""));

        return viewProperties;
    }

    @Override
    public String toString() {
        return displayName.get();
    }
}
