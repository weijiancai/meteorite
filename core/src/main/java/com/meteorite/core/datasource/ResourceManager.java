package com.meteorite.core.datasource;

/**
 * 资源管理
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ResourceManager {
    /**
     * 根据资源ID，获得资源信息
     *
     * @param rsId 资源ID
     * @return 返回资源信息
     */
    public static VirtualResource getResourceById(String rsId) {
        String[] strs = rsId.split("://");
        DataSource ds = DataSourceManager.getDataSourceByName(strs[0]);

        return ds.findResourceByPath(strs[1]);
    }
}
