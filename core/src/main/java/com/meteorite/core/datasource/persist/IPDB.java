package com.meteorite.core.datasource.persist;

import java.util.Map;

/**
 * 持久化数据库接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IPDB {
    /**
     * 获得持久化数据Map
     *
     * @return 返回持久化数据Map
     */
    Map<String, ? extends Map<String, Object>> getPDBMap();
}
