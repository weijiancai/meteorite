package com.meteorite.core.dict;

import com.meteorite.core.util.UString;

/**
 * 表单类型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum FormType {
    /**
     * 可编辑表单
     */
    EDIT,
    /**
     * 查询条件表单
     */
    QUERY,
    /**
     * 只读表单
     */
    READONLY
    ;

    public static FormType convert(String formTypeStr) {
        if (UString.isEmpty(formTypeStr)) {
            return EDIT;
        }

        return FormType.valueOf(formTypeStr);
    }
}
