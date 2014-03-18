package com.meteorite.core.ui;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.ui.config.ViewConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ViewManager {
    public static ViewConfig createConfig(Meta meta) {
        ViewConfig config = new ViewConfig();
        config.setId(0);
        config.setName(meta.getName());
        List<LayoutConfig> list = new ArrayList<LayoutConfig>();
//        Layout formLayout = LayoutConfigManager.getLayout(R.layout.FORM);
//        for (LayoutProperty property : formLayout.getProperties()) {
//            list.add(new LayoutConfig(formLayout.getName() + " " + property.getName(), property.getDisplayName(), property.getDefaultValue(), "", 10));
//        }


        return config;
    }
}
