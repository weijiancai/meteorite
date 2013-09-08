package com.meteorite.core.ui.layout.impl;

import com.meteorite.core.ui.layout.property.FormFieldProperty;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class FormFieldLayoutConfig extends BaseLayoutConfig {
    public FormFieldProperty getProperty() {
        return new FormFieldProperty();
    }
}
