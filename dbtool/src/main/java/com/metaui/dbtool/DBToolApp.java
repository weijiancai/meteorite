package com.metaui.dbtool;

import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.BaseApp;

/**
 * 数据库工具App
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBToolApp extends BaseApp {

    @Override
    public IFacade getFacade() {
        return DBToolFacade.getInstance();
    }

    public static void main(String[] args) {
        launch(DBToolApp.class);
    }
}
