package com.meteorite.core.ui.layout.impl;

import com.meteorite.core.ui.layout.LayoutProperty;
import com.meteorite.core.ui.layout.LayoutType;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class FormFieldLayout<T> extends BaseLayout<T> {
    public static final String NAME = "name";
    public static final String CNAME = "cname";
    public static final String FORM_TYPE = "formType";
    public static final String COL_COUNT = "colCount";
    public static final String COL_WIDTH = "colWidth";
    public static final String LABEL_GAP = "labelGap";
    public static final String FIELD_GAP = "fieldGap";
    public static final String HGAP = "hgap";
    public static final String VGAP = "vgap";

    public FormFieldLayout() {
        initProperties();
    }

    private void initProperties() {
        // 表单属性
        this.addProperty(LayoutType.FORM, new LayoutProperty(NAME, "名称", null, 10));
        this.addProperty(LayoutType.FORM, new LayoutProperty(CNAME, "中文名", null, 20));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_TYPE, "表单类型", null, 30));
        this.addProperty(LayoutType.FORM, new LayoutProperty(COL_COUNT, "列数", "3", 40));
        this.addProperty(LayoutType.FORM, new LayoutProperty(COL_WIDTH, "列宽", "180", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(LABEL_GAP, "labelGap", "5", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FIELD_GAP, "fieldGap", "15", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(HGAP, "hgap", "3", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(VGAP, "vgap", "5", 50));
    }

    private String getPropertyValue(String propertyName) {
        return super.getPropertyValue(LayoutType.FORM, propertyName);
    }
    private void setPropertyValue(String propertyName, String propertyValue) {
        super.setPropertyValue(LayoutType.FORM, propertyName, propertyValue);
    }

    @XmlAttribute
    public long getId() {
        return 0;
    }

    public void setId(long id) {

    }

    @XmlAttribute
    public String getName() {
        return getPropertyValue(NAME);
    }

    public void setName(String name) {
        setPropertyValue(NAME, name);
    }

    @XmlAttribute
    public String getCname() {
        return getPropertyValue(CNAME);
    }

    public void setCname(String cname) {
        setPropertyValue(CNAME, cname);
    }

    @XmlAttribute
    public String getFormType() {
        return getPropertyValue(FORM_TYPE);
    }

    public void setFormType(String formType) {
        setPropertyValue(FORM_TYPE, formType);
    }

    @XmlAttribute
    public int getColCount() {
        return Integer.parseInt(getPropertyValue(COL_COUNT));
    }

    public void setColCount(int colCount) {
        setPropertyValue(COL_COUNT, colCount + "");
    }

    @XmlAttribute
    public int getColWidth() {
        return Integer.parseInt(getPropertyValue(COL_WIDTH));
    }

    public void setColWidth(int colWidth) {
        setPropertyValue(COL_WIDTH, colWidth + "");
    }

    @XmlAttribute
    public int getLabelGap() {
        return Integer.parseInt(getPropertyValue(LABEL_GAP));
    }

    public void setLabelGap(int labelGap) {
        setPropertyValue(LABEL_GAP, labelGap + "");
    }

    @XmlAttribute
    public int getFieldGap() {
        return Integer.parseInt(getPropertyValue(FIELD_GAP));
    }

    public void setFieldGap(int fieldGap) {
        setPropertyValue(FIELD_GAP, fieldGap + "");
    }

    @XmlAttribute
    public int getHgap() {
        return Integer.parseInt(getPropertyValue(HGAP));
    }

    public void setHgap(int hgap) {
        setPropertyValue(HGAP, hgap + "");
    }

    @XmlAttribute
    public int getVgap() {
        return Integer.parseInt(getPropertyValue(VGAP));
    }

    public void setVgap(int vgap) {
        setPropertyValue(VGAP, vgap + "");
    }
}
