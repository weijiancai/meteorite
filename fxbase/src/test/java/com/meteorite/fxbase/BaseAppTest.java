package com.meteorite.fxbase;

import com.meteorite.core.facade.IFacade;
import com.meteorite.core.facade.impl.BaseFacade;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseAppTest extends BaseApp {
    @Override
    protected IFacade getFacade() {
        return new BaseFacade() {
            @Override
            protected void initProjectConfig() throws Exception {

            }

            @Override
            public void initAfter() throws Exception {

            }
        };
    }

    public static void main(String[] args) {
        launch(BaseAppTest.class);
    }
}
