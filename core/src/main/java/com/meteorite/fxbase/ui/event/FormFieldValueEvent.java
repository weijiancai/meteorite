package com.meteorite.fxbase.ui.event;

import com.meteorite.fxbase.ui.component.form.BaseFormField;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 表单字段值改变世间
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldValueEvent extends Event {
    public static EventType<FormFieldValueEvent> EVENT_TYPE = new EventType<>("FORM_FIELD_VALUE_EVENT_TYPE");
    private BaseFormField formField;
    private String oldValue;
    private String newValue;

    public FormFieldValueEvent(BaseFormField formField, String oldValue, String newValue) {
        super(EVENT_TYPE);
        this.formField = formField;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getName() {
        return formField.getConfig().getName();
    }

    public BaseFormField getFormField() {
        return formField;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }
}
