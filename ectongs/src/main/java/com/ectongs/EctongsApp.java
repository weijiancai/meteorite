package com.ectongs;

import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.BaseApp;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsApp extends BaseApp {
    @Override
    public IFacade getFacade() {
        return new EctongsFacade();
    }

    public static void main(String[] args) {
        launch(EctongsApp.class);
    }
}
