package com.meteorite.core.util;

import java.util.UUID;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
