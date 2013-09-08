package com.meteorite.core.ui.layout.impl;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FormLayout  {
    private FormProperty formProperty;

    public FormProperty getProperty() {
        /*if (formProperty == null) {
            formProperty = PropertyManager.getFormProperties();
        }*/
        return formProperty;
    }

    public <P> void setProperty(P property) {
    }

    public void setValues(Meta meta) {
        FormProperty form = getProperty();
        form.setName(meta.getName() + "Form");
        form.setCname(meta.getCname() + "表单");
//        metaForm.setInputDate(new Date());
//        metaForm.setValid(true);
        form.setColCount(1);
        form.setColWidth(180);
        form.setLabelGap(5);
        form.setFieldGap(15);
        form.setHgap(3);
        form.setVgap(5);

        int sortNum = 0;
        List<FormFieldProperty> formFields = new ArrayList<FormFieldProperty>();
        for (MetaField field : meta.getFileds()) {
            FormFieldProperty formField = new FormFieldProperty();
//            formField.setInputDate(new Date());
            formField.setSortNum(sortNum += 10);
//            formField.setValid(true);
            formField.setDisplayName(field.getDisplayName());
            formField.setDisplayStyle(DisplayStyle.TEXT_FIELD);
            formField.setForm(form);
            formField.setMetaField(field);
            formField.setSingleLine(false);
            formField.setDisplay(true);
            formField.setWidth(180);

            formFields.add(formField);
        }

        form.setFormFields(formFields);
    }

    public <T> T layout() {
        return null;
    }
}
