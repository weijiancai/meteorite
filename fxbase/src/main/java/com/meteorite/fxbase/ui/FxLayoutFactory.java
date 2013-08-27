package com.meteorite.fxbase.ui;

import com.meteorite.core.R;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.layout.FxFormLayout;
import javafx.scene.layout.Pane;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxLayoutFactory {
    public static Pane create(View view) {
        if (R.layout.FORM.equals(view.getLayout().getName())) {
            return new FxFormLayout(null).layout();
        }

        return null;
    }
}
