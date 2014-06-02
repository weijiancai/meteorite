package com.meteorite.core.datasource;

import com.meteorite.core.model.impl.BaseNavTreeNode;

/**
 * 资源项
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class ResourceItem extends BaseNavTreeNode {
    public abstract String getContent() throws Exception;
}
