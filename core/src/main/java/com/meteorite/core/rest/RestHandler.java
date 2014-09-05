package com.meteorite.core.rest;

import com.meteorite.core.datasource.VirtualResource;

import java.sql.SQLException;

/**
 * Reset Handler
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface RestHandler {
    VirtualResource get(Request request);

    void post(Request request);

    void put(Request request) throws Exception;

    void delete(Request request);


    /**
     * 导出资源
     */
    Response exp(Request request) throws Exception;

    /**
     * 导入资源
     */
    void imp(Request request) throws Exception;
}
