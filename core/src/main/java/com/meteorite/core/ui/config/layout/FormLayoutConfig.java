package com.meteorite.core.ui.config.layout;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.ui.config.LayoutProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FormLayoutConfig extends LayoutConfig {
    public FormLayoutConfig(Meta meta) {
        this.setName(meta.getName() + "Form");
        this.setDisplayName(meta.getCname() + "表单");
        List<ILayoutProperty> properties = new ArrayList<ILayoutProperty>();
        this.setProperties(properties);

//        properties.add(new LayoutProperty())
    }
}
