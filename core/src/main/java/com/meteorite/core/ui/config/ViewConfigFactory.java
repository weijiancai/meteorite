package com.meteorite.core.ui.config;

import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.layout.LayoutManager;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ViewConfigFactory {
    public static ViewConfig create(Meta meta, Layout layout) {
        ViewConfig config = new ViewConfig();

        return config;
    }

    public static IViewConfig createFormConfig(Meta meta) {
        ViewConfig config = new ViewConfig();
        config.setName(meta.getName() + "View");
        config.setDisplayName(meta.getDisplayName() + "视图");
        config.setLayoutConfig(LayoutManager.createFormLayout(meta));

        return config;
    }
}
