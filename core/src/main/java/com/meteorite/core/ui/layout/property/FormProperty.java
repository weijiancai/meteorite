package com.meteorite.core.ui.layout.property;

import com.meteorite.core.R;
import com.meteorite.core.ui.model.Action;
import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.util.UNumber;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * 表单属性
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormProperty {
    private String name;
    private String cname;
    private String formType;
    private int colCount;
    private int colWidth;
    private int labelGap;
    private int fieldGap;
    private int hgap;
    private int vgap;

    private List<FormFieldProperty> formFields = new ArrayList<FormFieldProperty>();
    private List<Action> actions;
    
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @XmlAttribute
    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    @XmlAttribute
    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    @XmlAttribute
    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    @XmlAttribute
    public int getLabelGap() {
        return labelGap;
    }

    public void setLabelGap(int labelGap) {
        this.labelGap = labelGap;
    }

    @XmlAttribute
    public int getFieldGap() {
        return fieldGap;
    }

    public void setFieldGap(int fieldGap) {
        this.fieldGap = fieldGap;
    }

    @XmlAttribute
    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    @XmlAttribute
    public int getVgap() {
        return vgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public List<FormFieldProperty> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormFieldProperty> formFields) {
        this.formFields = formFields;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
