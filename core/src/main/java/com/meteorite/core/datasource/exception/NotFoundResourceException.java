package com.meteorite.core.datasource.exception;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class NotFoundResourceException extends RuntimeException{
    public NotFoundResourceException(String path) {
        super("未找到资源：" + path);
    }
}
