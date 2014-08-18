package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.VirtualResource;

/**
 * 数据库资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBResource extends VirtualResource {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public VirtualResource getParent() {
        return null;
    }

    @Override
    public VirtualResource[] getChildren() {
        return new VirtualResource[0];
    }

    @Override
    public void delete(Object requestor) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}
