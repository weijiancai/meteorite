package com.meteorite.core.ui.layout.impl;

import com.meteorite.core.ui.layout.property.FormFieldProperties;

/**
 * 表单字段布局器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class FormFieldLayout extends BaseLayout {
    @Override
    public FormFieldProperties getProperties() {
        return new FormFieldProperties();
    }
}
