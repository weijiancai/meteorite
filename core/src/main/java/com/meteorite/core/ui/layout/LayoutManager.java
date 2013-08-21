package com.meteorite.core.ui.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayout;
import com.meteorite.core.ui.layout.impl.FormFieldLayout;
import com.meteorite.core.ui.layout.impl.FormLayout;
import com.meteorite.core.ui.layout.model.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutManager {

    public static void load() {
        Layout formLayout = new Layout(1, 0, "FORM", "表单", "", 10);
        Layout formLabelLayout = new Layout(2, 1, "FORM_LABEL", "表单Label", "", 10);
        Layout formFieldLayout = new Layout(3, 1, "FORM_FIELD", "表单Field", "", 20);
    }
}
