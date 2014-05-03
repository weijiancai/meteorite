package com.meteorite.core.datasource.db.object;

import com.meteorite.core.datasource.db.object.enums.DBTriggerEvent;
import com.meteorite.core.datasource.db.object.enums.DBTriggerType;

import java.util.List;

/**
 * 数据库触发器
 *
 * @author wei_jc
 * @since  1.0.0
 */
public interface DBTrigger extends DBObject {
    /**
     * 获得表信息
     *
     * @return 返回表信息
     * @since 1.0.0
     */
    DBTable getTable();

    /**
     * 是否每行执行触发器
     *
     * @return 如果是每行执行返回true，否则返回false
     * @since 1.0.0
     */
    boolean isForEachRow();

    /**
     * 获得触发器类型
     *
     * @return 返回触发器类型
     * @since 1.0.0
     */
    DBTriggerType getTriggerType();

    /**
     * 获得触发器事件信息
     *
     * @return 返回触发器事件列表
     * @since 1.0.0
     */
    List<DBTriggerEvent> getTriggerEvents();
}
