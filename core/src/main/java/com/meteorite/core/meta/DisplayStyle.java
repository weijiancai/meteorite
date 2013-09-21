package com.meteorite.core.meta;

/**
 * 显示样式
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum DisplayStyle {
    /**
     * 0. 文本框
     */
    TEXT_FIELD,
    /**
     * 1. 文本域
     */
    TEXT_AREA,
    /**
     * 2. 密码
     */
    PASSWORD,
    /**
     * 3. 下拉框
     */
    COMBO_BOX,
    /**
     * 4. 数据源
     */
    DATA_SOURCE
    ;

    public static DisplayStyle getStyle(String styleStr) {
        if ("0".equals(styleStr)) {
            return TEXT_FIELD;
        } else if ("1".equals(styleStr)) {
            return TEXT_AREA;
        } else if ("2".equals(styleStr)) {
            return PASSWORD;
        } else if ("3".equals(styleStr)) {
            return COMBO_BOX;
        } else if ("4".equals(styleStr)) {
            return DATA_SOURCE;
        }

        return TEXT_FIELD;
    }
}
