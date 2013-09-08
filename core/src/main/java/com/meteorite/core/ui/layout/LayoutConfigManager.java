package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayoutConfig;
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
public class LayoutConfigManager {
    private static Map<String, ILayoutConfig> cache = new HashMap<String, ILayoutConfig>();

    public static void load() {
        iterator(ProjectConfigFactory.getProjectConfig().getLayout());
    }

    private static void iterator(ILayoutConfig layout) {
        cache.put(layout.getName(), layout);
        if (layout.getChildren() != null && layout.getChildren().size() > 0) {
            for (ILayoutConfig child : layout.getChildren()) {
                iterator(child);
            }
        }
    }

    public static ILayoutConfig getLayout(String name) {
        return cache.get(name);
    }

    public static ILayoutConfig createLayout(Meta meta) {
        ILayoutConfig layout = getLayout(R.layout.FORM);
        return layout;
    }

    public static ILayoutConfig createFormLayout(Meta meta) {
        ILayoutConfig layout = getLayout(R.layout.FORM);


        return layout;
    }
}
