package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.impl.FormLayout;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.ui.model.LayoutProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutManager {
    private static Map<String, Layout> cache = new HashMap<String, Layout>();

    public static void load() {
        iterator(ProjectConfigFactory.getProjectConfig().getLayout());
    }

    private static void iterator(Layout layout) {
        cache.put(layout.getName(), layout);
        if (layout.getChildren() != null && layout.getChildren().size() > 0) {
            for (Layout child : layout.getChildren()) {
                iterator(child);
            }
        }
    }

    public static Layout getLayout(String name) {
        return cache.get(name);
    }

    public static Layout createLayout(Meta meta) {
        Layout layout = getLayout(R.layout.FORM);
        return layout;
    }

    public static Layout createFormLayout(Meta meta) {
        Layout layout = getLayout(R.layout.FORM);


        return layout;
    }
}
