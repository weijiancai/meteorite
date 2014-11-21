package com.meteorite.core.meta;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.meta.model.Meta;

/**
 * 元数据监听器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface MetaListener {
    /**
     * 新增之前
     *
     * @param meta 元数据
     * @param rowData 行数据
     */
    void addPrep(Meta meta, DataMap rowData);

    /**
     * 新增之后
     *
     * @param meta 元数据
     * @param rowData 行数据
     */
    void addEnd(Meta meta, DataMap rowData);
}
