package com.meteorite.core.rest;

import com.meteorite.core.datasource.VirtualResource;

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
}
