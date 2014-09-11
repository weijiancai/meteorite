package com.meteorite.core.rest;

import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.request.IRequest;
import com.meteorite.core.datasource.request.IResponse;

/**
 * Reset Handler
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface RestHandler {
    VirtualResource get(IRequest request);

    void post(IRequest request);

    void put(IRequest request) throws Exception;

    void delete(IRequest request);


    /**
     * 导出资源
     */
    IResponse exp(IRequest request) throws Exception;

    /**
     * 导入资源
     */
    void imp(IRequest request) throws Exception;
}
