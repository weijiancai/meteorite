package com.meteorite.fxbase.ui.design;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.ui.IFormField;
import com.meteorite.fxbase.ui.component.FxFormPane;

/**
 * JavaFx 表单设计面板
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class FxFormDesignPane extends FxFormPane {

    public FxFormDesignPane(ILayoutConfig formLayoutConfig, ILayoutConfig fieldLayoutConfig) {
        super(formLayoutConfig, false);

        initDesignPane();
    }

    private void initDesignPane() {
        for (IFormField field : formFieldList) {

        }
    }


}
