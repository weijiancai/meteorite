package com.meteorite.core.util;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public interface Callback<T> {
    void call(T t, Object... obj) throws Exception;
}
