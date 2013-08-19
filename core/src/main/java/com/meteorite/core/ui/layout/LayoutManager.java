package com.meteorite.core.ui.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayout;
import com.meteorite.core.ui.layout.impl.FormFieldLayout;
import com.meteorite.core.ui.layout.impl.FormLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutManager {

    public static ILayout getFormLayout(Meta meta) {
        FormLayout form = new FormLayout();
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
        List<FormFieldLayout> formFields = new ArrayList<FormFieldLayout>();
        for (MetaField field : meta.getFileds()) {
            FormFieldLayout formField = new FormFieldLayout();
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

        return form;
    }
}
