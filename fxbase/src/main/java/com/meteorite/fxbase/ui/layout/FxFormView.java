package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.ui.ILayout;
import com.meteorite.core.ui.IView;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView<L extends ILayout> implements IView<L> {
    private L layout;

    public FxFormView(L layout) {
        this.layout = layout;
    }

    @Override
    public void initUI() {

    }

    @Override
    public L getLayout() {
        return layout;
    }
}
