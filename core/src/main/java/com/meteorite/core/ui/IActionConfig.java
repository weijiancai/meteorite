package com.meteorite.core.ui;

/**
 * UI布局Action接口
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IActionConfig {
    /**
     * 获得Action Id
     *
     * @return 返回Action Id
     */
    int getId();

    /**
     * 获得Action名称
     *
     * @return 返回Action名称
     */
    String getName();

    /**
     * 获得Action显示名
     *
     * @return 返回Action显示名
     */
    String getDisplayName();

    /**
     * 获得Action排序号
     *
     * @return 返回Action排序号
     */
    int getSortNum();
}
