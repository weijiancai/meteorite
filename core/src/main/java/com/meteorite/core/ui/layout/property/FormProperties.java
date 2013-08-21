package com.meteorite.core.ui.layout.property;

import com.meteorite.core.ui.layout.LayoutType;
import com.meteorite.core.ui.layout.model.LayoutProperty;
import com.meteorite.core.util.UNumber;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * 表单属性
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormProperties extends LayoutProperties {
    public static final String FORM_NAME = "name";
    public static final String FORM_CNAME = "cname";
    public static final String FORM_TYPE = "formType";
    public static final String FORM_COL_COUNT = "colCount";
    public static final String FORM_COL_WIDTH = "colWidth";
    public static final String FORM_LABEL_GAP = "labelGap";
    public static final String FORM_FIELD_GAP = "fieldGap";
    public static final String FORM_HGAP = "hgap";
    public static final String FORM_VGAP = "vgap";

    private List<FormFieldProperties> formFields;

    public FormProperties() {
        initProperties();
    }

    private void initProperties() {
        // 表单属性
        /*this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_NAME, "名称", null, 10));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_CNAME, "中文名", null, 20));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_TYPE, "表单类型", null, 30));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_COL_COUNT, "列数", "3", 40));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_COL_WIDTH, "列宽", "180", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_LABEL_GAP, "labelGap", "5", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_FIELD_GAP, "fieldGap", "15", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_HGAP, "hgap", "3", 50));
        this.addProperty(LayoutType.FORM, new LayoutProperty(FORM_VGAP, "vgap", "5", 50));*/
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
        return getPropertyValue(FORM_NAME);
    }

    public void setName(String name) {
        setPropertyValue(FORM_NAME, name);
    }

    @XmlAttribute
    public String getCname() {
        return getPropertyValue(FORM_CNAME);
    }

    public void setCname(String cname) {
        setPropertyValue(FORM_CNAME, cname);
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
        return UNumber.toInt(getPropertyValue(FORM_COL_COUNT));
    }

    public void setColCount(int colCount) {
        setPropertyValue(FORM_COL_COUNT, colCount + "");
    }

    @XmlAttribute
    public int getColWidth() {
        return UNumber.toInt(getPropertyValue(FORM_COL_WIDTH));
    }

    public void setColWidth(int colWidth) {
        setPropertyValue(FORM_COL_WIDTH, colWidth + "");
    }

    @XmlAttribute
    public int getLabelGap() {
        return UNumber.toInt(getPropertyValue(FORM_LABEL_GAP));
    }

    public void setLabelGap(int labelGap) {
        setPropertyValue(FORM_LABEL_GAP, labelGap + "");
    }

    @XmlAttribute
    public int getFieldGap() {
        return UNumber.toInt(getPropertyValue(FORM_FIELD_GAP));
    }

    public void setFieldGap(int fieldGap) {
        setPropertyValue(FORM_FIELD_GAP, fieldGap + "");
    }

    @XmlAttribute
    public int getHgap() {
        return UNumber.toInt(getPropertyValue(FORM_HGAP));
    }

    public void setHgap(int hgap) {
        setPropertyValue(FORM_HGAP, hgap + "");
    }

    @XmlAttribute
    public int getVgap() {
        return UNumber.toInt(getPropertyValue(FORM_VGAP));
    }

    public void setVgap(int vgap) {
        setPropertyValue(FORM_VGAP, vgap + "");
    }

    public List<FormFieldProperties> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldProperties> formFields) {
        this.formFields = formFields;
    }
}
