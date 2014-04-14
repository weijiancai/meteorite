package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.fxbase.ui.component.FxFormPane;
import com.meteorite.fxbase.ui.view.FxPane;
import javafx.scene.layout.Pane;

import static com.meteorite.core.ui.ConfigConst.LAYOUT_FORM;
/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxLayoutFactory {
    public static FxPane create(IViewConfig viewConfig, boolean isDesign) {
        if (LAYOUT_FORM.equals(viewConfig.getLayoutConfig().getName())) {
            return new FxFormPane(viewConfig.getLayoutConfig());
        }

        return null;
    }
}
