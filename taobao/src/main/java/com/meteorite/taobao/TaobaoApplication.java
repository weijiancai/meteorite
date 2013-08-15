package com.meteorite.taobao;

import com.meteorite.core.facade.IFacade;
import com.meteorite.fxbase.BaseApplication;

/**
 * 淘宝主应用程序
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoApplication extends BaseApplication {
    @Override
    protected IFacade getFacade() {
        return TaobaoFacade.getInstance();
    }

    public static void main(String[] args) {
        launch(TaobaoApplication.class);
    }
}
