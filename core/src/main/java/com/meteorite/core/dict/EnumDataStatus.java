package com.meteorite.core.dict;

/**
 * 数据状态枚举
 *
 * @author wei_jc
 * @since 1.0.0
 */
public enum EnumDataStatus {
    /**
     * 新增之前
     */
    ADD_BEFORE,
    /**
     * 新增之后
     */
    ADD_AFTER,
    /**
     * 更新之前
     */
    UPDATE_BEFORE,
    /**
     * 更新之后
     */
    UPDATE_END;
}
