package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.IViewConfig;
import javafx.scene.layout.Pane;

import static com.meteorite.core.ui.ConfigConst.*;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxViewFactory {
    public static IView<FxPane> getView(IViewConfig config) {
        if (LAYOUT_FORM.equals(config.getLayoutConfig().getName())) {
            return new FxFormView(config);
        }
        return null;
    }
}
