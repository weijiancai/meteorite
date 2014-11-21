package com.meteorite.core.config;

/**
 * 系统监听器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SystemListener {
    private static SystemListener ourInstance = new SystemListener();

    public static SystemListener getInstance() {
        return ourInstance;
    }

    private SystemListener() {
    }

    public void init() {
        addMetaListener();
    }

    private void addMetaListener() {

    }
}
