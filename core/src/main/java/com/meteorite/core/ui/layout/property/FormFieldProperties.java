package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutType;
import com.meteorite.core.ui.layout.model.LayoutProperty;
import com.meteorite.core.util.UNumber;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormFieldProperties extends LayoutProperties {
    public static final String DISPLAY_NAME = "displayName";
    public static final String IS_SINGLE_LINE = "isSingleLine";
    public static final String IS_DISPLAY = "isDisplay";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String DISPLAY_STYLE = "displayStyle";
    public static final String SORT_NUM = "sortNum";

    private FormProperties form;
    private MetaField metaField;

    public FormFieldProperties() {
        initProperties();
    }

    private void initProperties() {
        // 表单字段属性
        /*this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(DISPLAY_NAME, "显示名", null, 10));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(IS_SINGLE_LINE, "独行", null, 20));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(IS_DISPLAY, "显示", null, 30));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(WIDTH, "宽", null, 40));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(HEIGHT, "高", null, 50));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(DISPLAY_STYLE, "显示风格", "string", 60));
        this.addProperty(LayoutType.FORM_ITEM_FIELD, new LayoutProperty(SORT_NUM, "排序号", "0", 70));*/
    }

    private String getPropertyValue(String propertyName) {
        return super.getPropertyValue(LayoutType.FORM_ITEM_FIELD, propertyName);
    }
    private void setPropertyValue(String propertyName, String propertyValue) {
        super.setPropertyValue(LayoutType.FORM_ITEM_FIELD, propertyName, propertyValue);
    }

    @XmlAttribute
    public long getId() {
        return 0;
    }

    public void setId(long id) {

    }

    @XmlAttribute
    public String getDisplayName() {
        return getPropertyValue(DISPLAY_NAME);
    }

    public void setDisplayName(String displayName) {
        setPropertyValue(DISPLAY_NAME, displayName);
    }

    @XmlAttribute
    public boolean isSingleLine() {
        return Boolean.valueOf(getPropertyValue(IS_SINGLE_LINE));
    }

    public void setSingleLine(boolean singleLine) {
        setPropertyValue(IS_SINGLE_LINE, singleLine + "");
    }

    @XmlAttribute
    public boolean isDisplay() {
        return Boolean.valueOf(getPropertyValue(IS_DISPLAY));
    }

    public void setDisplay(boolean display) {
        setPropertyValue(IS_DISPLAY, display + "");
    }

    /*@XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }*/

    @XmlAttribute
    public int getWidth() {
        return UNumber.toInt(getPropertyValue(WIDTH));
    }

    public void setWidth(int width) {
        setPropertyValue(WIDTH, width + "");
    }

    @XmlAttribute
    public int getHeight() {
        return UNumber.toInt(getPropertyValue(HEIGHT));
    }

    public void setHeight(int height) {
        setPropertyValue(HEIGHT, height + "");
    }

    @XmlAttribute
    public DisplayStyle getDisplayStyle() {
        return DisplayStyle.getStyle(getPropertyValue(DISPLAY_STYLE));
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        setPropertyValue(DISPLAY_STYLE, displayStyle.ordinal() + "");
    }

    /*@XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }*/

    @XmlAttribute
    public int getSortNum() {
        return UNumber.toInt(getPropertyValue(SORT_NUM));
    }

    public void setSortNum(int sortNum) {
        setPropertyValue(SORT_NUM, sortNum + "");
    }

    /*@XmlTransient
    public MetaForm getForm() {
        return form;
    }

    public void setForm(MetaForm form) {
        this.form = form;
    }*/

    public FormProperties getForm() {
        return form;
    }

    public void setForm(FormProperties form) {
        this.form = form;
    }

    @XmlTransient
    public MetaField getMetaField() {
        return metaField;
    }

    public void setMetaField(MetaField metaField) {
        this.metaField = metaField;
    }
}
