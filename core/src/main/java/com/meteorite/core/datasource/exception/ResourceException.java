package com.meteorite.core.datasource.exception;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ResourceException extends RuntimeException {
    public static int NOT_FOUND_RESOURCE = 1;

    public ResourceException(int type) {
        if(type == NOT_FOUND_RESOURCE) {
        }
    }

    public ResourceException(String message) {
        super(message);
    }
}
