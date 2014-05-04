package com.meteorite.core.datasource.db.object;

import java.util.List;

/**
 * 数据库表
 *
 * @author wei_jc
 * @since  1.0.0
 */
public interface DBTable extends DBDataset {
    /**
     * 获得表的索引信息
     *
     * @return 返回表的索引列表
     * @since 1.0.0
     */
    List<DBIndex> getIndexes();

    /**
     * 获得表的触发器信息
     *
     * @return 返回表的触发器列表
     * @since 1.0.0
     */
    List<DBTrigger> getTriggers();
}
