package com.meteorite.core.datasource;

import com.meteorite.core.model.impl.BaseTreeNode;

import java.io.InputStream;
import java.net.URI;

/**
 * 资源项
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class ResourceItem extends BaseTreeNode {
    public abstract String getContent() throws Exception;

    public abstract InputStream getInputStream() throws Exception;

    public abstract URI getURI() throws Exception;
}
