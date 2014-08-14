package com.meteorite.core.ui.layout;

import com.meteorite.core.ui.model.LayoutProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性管理器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class PropertyManager {
    public static List<LayoutProperty> buildFormField() {
        List<LayoutProperty> list = new ArrayList<LayoutProperty>();
        list.add(PropBuilder.create().name(PropertyNames.FORM_FIELD.NAME).displayName("名称").layoutType(LayoutType.FORM_FIELD).build());
        return list;
    }
}
