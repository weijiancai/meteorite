package com.meteorite.taobao;

import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.BaseApp;

/**
 * 淘宝主应用程序
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoApp extends BaseApp {
    @Override
    protected IFacade getFacade() {
        return TaobaoFacade.getInstance();
    }

    public static void main(String[] args) {
        launch(TaobaoApp.class);
    }
}
