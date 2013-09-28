package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumBoolean;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.util.UString;

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

        List<ILayoutConfig> children = new ArrayList<>();
        for (MetaField field : meta.getFileds()) {
            ILayoutConfig formField = getLayout(R.layout.FORM_FIELD);
            formField.setPropValue(FORM_FIELD_NAME, field.getName());
            formField.setPropValue(FORM_FIELD_DISPLAY_NAME, field.getDisplayName());
            formField.setPropValue(FORM_FIELD_SORT_NUM, field.getSortNum() + "");
            formField.setPropValue(FORM_FIELD_DATA_TYPE, field.getDataType() + "");
            if (MetaDataType.DATA_SOURCE == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.DATA_SOURCE.name());
                formField.setPropValue(FORM_FIELD_IS_SINGLE_LINE, "true");
            } else if (MetaDataType.PASSWORD == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.PASSWORD.name());
            } else if (MetaDataType.DICT == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.COMBO_BOX.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, field.getDictId());
            } else if (MetaDataType.BOOLEAN == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.BOOLEAN.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, EnumBoolean.class.getName());
            }
            children.add(formField);
        }
        form.setChildren(children);

        return form;
    }

    /**
     * 将布局配置信息转化为form表单配置
     *
     * @param layoutConfig 布局配置
     * @return 返回布form表单布局配置
     */
    public static ILayoutConfig createFormLayout(ILayoutConfig layoutConfig) {
        ILayoutConfig form = getLayout(R.layout.FORM);
        form.setPropValue(FORM_NAME, layoutConfig.getName() + "Form");
        form.setPropValue(FORM_DISPLAY_NAME, layoutConfig.getDisplayName() + "表单");

        List<ILayoutConfig> children = new ArrayList<>();
        for (ILayoutProperty field : layoutConfig.getProperties()) {
            if ("value".equals(field.getName())) {
                continue;
            }
            ILayoutConfig formField = getLayout(R.layout.FORM_FIELD);
            formField.setPropValue(FORM_FIELD_NAME, field.getName());
            formField.setPropValue(FORM_FIELD_DISPLAY_NAME, field.getDisplayName());
            formField.setPropValue(FORM_FIELD_SORT_NUM, field.getSortNum() + "");
            formField.setPropValue(FORM_FIELD_DATA_TYPE, field.getDataType() + "");
            if (MetaDataType.DATA_SOURCE == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.DATA_SOURCE.name());
                formField.setPropValue(FORM_FIELD_IS_SINGLE_LINE, "true");
            } else if (MetaDataType.PASSWORD == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.PASSWORD.name());
            } else if (MetaDataType.DICT == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.COMBO_BOX.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, field.getDictId());
            } else if (MetaDataType.BOOLEAN == field.getDataType()) {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.BOOLEAN.name());
                formField.setPropValue(FORM_FIELD_DICT_ID, EnumBoolean.class.getName());
            } else {
                formField.setPropValue(FORM_FIELD_DISPLAY_STYLE, DisplayStyle.TEXT_FIELD.name());
            }
            formField.setPropValue(FORM_FIELD_VALUE, UString.isEmpty(field.getValue()) ? field.getDefaultValue() : field.getValue());
            children.add(formField);
        }
        form.setChildren(children);

        return form;
    }
}
