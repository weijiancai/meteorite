package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.LayoutConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.ui.ConfigConst.*;
/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutConfigManager {
    private static Map<String, ILayoutConfig> cache = new HashMap<String, ILayoutConfig>();

    public static void load() {
        LayoutConfig root = SystemManager.getInstance().getLayoutConfig();
        if (root != null) {
            iterator(root);
        }
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
        ILayoutConfig config = cache.get(name);
        if (config == null) {
            throw new RuntimeException(String.format("获取【%s】布局配置信息失败！", name));
        }
        return config.clone();
    }

    public static ILayoutConfig createLayout(Meta meta) {
        ILayoutConfig layout = getLayout(R.layout.FORM);
        return layout;
    }

    public static ILayoutConfig createFormLayout(Meta meta) {
        ILayoutConfig form = getLayout(R.layout.FORM);
        form.setPropValue(FORM_NAME, meta.getName() + "Form");
        form.setPropValue(FORM_DISPLAY_NAME, meta.getDisplayName() + "表单");

        List<ILayoutConfig> children = new ArrayList<ILayoutConfig>();
        for (MetaField field : meta.getFileds()) {
            ILayoutConfig formField = getLayout(R.layout.FORM_FIELD);
            formField.setPropValue(FORM_FIELD_NAME, field.getName());
            formField.setPropValue(FORM_FIELD_DISPLAY_NAME, field.getDisplayName());
            formField.setPropValue(FORM_FIELD_SORT_NUM, field.getSortNum() + "");
            children.add(formField);
        }
        form.setChildren(children);

        return form;
    }
}
