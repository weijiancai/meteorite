package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.ui.IViewConfig;
import javafx.scene.layout.Pane;

import static com.meteorite.core.ui.ConfigConst.LAYOUT_FORM;
/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxLayoutFactory {
    public static Pane create(IViewConfig viewConfig) {
        if (LAYOUT_FORM.equals(viewConfig.getLayoutConfig().getName())) {
            return new FxFormLayout(viewConfig).layout();
        }

        return null;
    }
}
