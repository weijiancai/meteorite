package com.meteorite.core.ui.config;

import com.meteorite.core.meta.model.Meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FormViewConfig extends ViewConfig {
    public FormViewConfig(Meta meta) {
        this.setName(meta.getName() + "View");
        this.setDisplayName(meta.getDisplayName() + "视图");

        LayoutConfig formLayout = new LayoutConfig();
        this.setLayoutConfig(formLayout);


        List<LayoutConfig> list = new ArrayList<LayoutConfig>();
//        LayoutProperty formNameProp = formLayout.getProperty(R.layout.prop.form.NAME);
//        list.add(getLayoutConfig(this, formNameProp, meta.getName() + "Form"));
    }
}
