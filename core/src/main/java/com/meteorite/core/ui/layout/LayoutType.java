package com.meteorite.core.ui.layout;

import com.meteorite.core.dict.annotation.DictElement;

/**
 * 布局类型
 *
 * @author wei_jc
 * @since  1.0.0
 */
@DictElement(categoryName = "布局类型")
public enum LayoutType {
    /**
     * 表单
     */
    FORM,
    /**
     * 表单字段
     */
    FORM_FIELD,
    /**
     * 表格
     */
    TABLE,
    /**
     * 表格字段
     */
    TABLE_FIELD,
    /**
     * CRUD
     */
    CRUD
}
