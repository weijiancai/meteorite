package com.meteorite.core.datasource;

import com.meteorite.core.util.UString;

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
        if (UString.isEmpty(rsId)) {
            return null;
        }

        String[] strs = rsId.split("://");
        DataSource ds = DataSourceManager.getDataSourceByName(strs[0]);
        String path = strs[1];
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        return ds.findResourceByPath(path);
    }
}
