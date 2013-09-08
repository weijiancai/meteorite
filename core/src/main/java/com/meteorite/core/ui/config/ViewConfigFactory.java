package com.meteorite.core.ui.config;

import com.meteorite.core.R;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.layout.LayoutConfigManager;
import com.meteorite.core.ui.model.Layout;

import java.util.ArrayList;
import java.util.List;

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
        ILayoutConfig formLayout = LayoutConfigManager.getLayout(R.layout.FORM);

        ViewConfig config = new ViewConfig();
        config.setName(meta.getName() + "View");
        config.setDisplayName(meta.getCname() + "视图");
        config.setLayoutConfig(formLayout);

        LayoutConfig form = new LayoutConfig();
        form.setName(meta.getName() + "Form");
        form.setDisplayName(meta.getCname() + "表单");
        List<ILayoutProperty> formProp = new ArrayList<ILayoutProperty>();
        form.setProperties(formProp);

//        formProp.add(new LayoutProperty());

//        LayoutProperty formNameProp = formLayout.getProperty(R.layout.prop.form.NAME);
//        list.add(getLayoutConfig(config, formNameProp, meta.getName() + "Form"));


        /*list.add(new LayoutConfig("FORM.name", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.cname", meta.getCname() + "表单", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.formType", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.colCount", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.colWidth", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.labelGap", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.fieldGap", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.hgap", meta.getName() + "Form", meta.getSortNum()));
        list.add(new LayoutConfig("FORM.vgap", meta.getName() + "Form", meta.getSortNum()));*/


//        formLayout.setPropertyValue(R.layout.prop.form.NAME, "");


        for (MetaField field : meta.getFileds()) {

        }
//        return create(meta, LayoutConfigManager.getLayout(R.layout.FORM));
        return null;
    }

    private static LayoutConfig getLayoutConfig(ViewConfig view, LayoutProperty property, String value) {
        return new LayoutConfig();
    }
}
