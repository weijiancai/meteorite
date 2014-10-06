package com.meteorite.fxbase.ui.event;

import com.meteorite.fxbase.ui.component.form.BaseFormField;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 表单字段鼠标单击事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FormFieldClickEvent extends Event {
    public static EventType<FormFieldClickEvent> EVENT_TYPE = new EventType<FormFieldClickEvent>("FORM_FIELD_CLICK_EVENT_TYPE");
    private BaseFormField formField;

    public FormFieldClickEvent(BaseFormField formField) {
        super(EVENT_TYPE);
        this.formField = formField;
    }

    public String getName() {
        return formField.getConfig().getName();
    }

    public BaseFormField getFormField() {
        return formField;
    }
}
