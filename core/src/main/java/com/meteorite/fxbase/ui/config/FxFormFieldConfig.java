package com.meteorite.fxbase.ui.config;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import javafx.beans.property.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxFormFieldConfig extends FormFieldConfig {
    private StringProperty nameProperty = new SimpleStringProperty();
    private StringProperty displayNameProperty = new SimpleStringProperty();
    private BooleanProperty singleLineProperty = new SimpleBooleanProperty();
    private BooleanProperty displayProperty = new SimpleBooleanProperty();
    private IntegerProperty widthProperty = new SimpleIntegerProperty();
    private IntegerProperty heightProperty = new SimpleIntegerProperty();
    private ObjectProperty<DisplayStyle> displayStyleProperty = new SimpleObjectProperty<DisplayStyle>();
    private ObjectProperty<MetaDataType> dataTypeProperty = new SimpleObjectProperty<MetaDataType>();
    private ObjectProperty<DictCategory> dictProperty = new SimpleObjectProperty<DictCategory>();
    private IntegerProperty sortNumProperty = new SimpleIntegerProperty();

    public FxFormFieldConfig(FormConfig formConfig, ILayoutConfig config) {
        super(formConfig, config);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void setName(String name) {
        super.setName(name);
        nameProperty.set(name);
    }

    public StringProperty displayNameProperty() {
        return displayNameProperty;
    }

    public void setDisplayName(String displayName) {
        super.setDisplayName(displayName);
        displayNameProperty.set(displayName);
    }

    public BooleanProperty singleLineProperty() {
        return singleLineProperty;
    }

    public void setSingleLine(boolean isSingleLine) {
        super.setSingleLine(isSingleLine);
        singleLineProperty.setValue(isSingleLine);
    }

    public BooleanProperty displayProperty() {
        return displayProperty;
    }

    @Override
    public void setDisplay(boolean display) {
        super.setDisplay(display);
        displayProperty.set(display);
    }

    public IntegerProperty widthProperty() {
        return widthProperty;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        widthProperty.set(width);
    }

    public IntegerProperty heightProperty() {
        return heightProperty;
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        heightProperty.set(height);
    }

    public ObjectProperty<DisplayStyle> displayStyleProperty() {
        return displayStyleProperty;
    }

    @Override
    public void setDisplayStyle(DisplayStyle displayStyle) {
        super.setDisplayStyle(displayStyle);
        displayStyleProperty.set(displayStyle);
    }

    public ObjectProperty<MetaDataType> dataTypeProperty() {
        return dataTypeProperty;
    }

    @Override
    public void setDataType(MetaDataType dataType) {
        super.setDataType(dataType);
        dataTypeProperty.set(dataType);
    }

    public ObjectProperty<DictCategory> dictCategoryProperty() {
        return dictProperty;
    }

    @Override
    public void setDict(String dictId) {
        super.setDict(dictId);
        dictProperty.set(DictManager.getDict(dictId));
    }

    public IntegerProperty sortNumProperty() {
        return sortNumProperty;
    }

    @Override
    public void setSortNum(int sortNum) {
        super.setSortNum(sortNum);
        sortNumProperty.set(sortNum);
    }
}
