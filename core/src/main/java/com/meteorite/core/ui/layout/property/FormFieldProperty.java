package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.MetaField;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormFieldProperty {
    private String displayName;
    private boolean isSingleLine;
    private boolean isDisplay;
    private int width;
    private int height;
    private DisplayStyle displayStyle;
    private int sortNum;

    private FormProperty form;
    private MetaField metaField;

    public FormFieldProperty() {

    }

    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public boolean isSingleLine() {
        return isSingleLine;
    }

    public void setSingleLine(boolean singleLine) {
        this.isSingleLine = singleLine;
    }

    @XmlAttribute
    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        this.isDisplay = display;
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
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @XmlAttribute
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @XmlAttribute
    public DisplayStyle getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
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
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    /*@XmlTransient
    public MetaForm getForm() {
        return form;
    }

    public void setForm(MetaForm form) {
        this.form = form;
    }*/

    public FormProperty getForm() {
        return form;
    }

    public void setForm(FormProperty form) {
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
