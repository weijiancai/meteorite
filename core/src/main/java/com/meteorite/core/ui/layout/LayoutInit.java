package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.ui.config.ActionConfig;
import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.ui.config.LayoutProperty;
import com.meteorite.core.ui.model.Layout;

import static com.meteorite.core.ui.ConfigConst.*;

/**
 * 布局初始化
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutInit {
    private static final LayoutInit INSTANCE = new LayoutInit();
    private static int count = 0;

    private LayoutInit() {}

    public static LayoutInit getInstance() {
        return INSTANCE;
    }

    public static LayoutConfig getRoot() {
        LayoutConfig root = new LayoutConfig(R.layout.ROOT, "布局管理器", "", null);
        LayoutConfig formLayout = new LayoutConfig(R.layout.FORM, "表单", "", root);
        LayoutConfig formLabelLayout = new LayoutConfig(R.layout.FORM_LABEL, "表单Label", "", formLayout);
        LayoutConfig formFieldLayout = new LayoutConfig(R.layout.FORM_FIELD, "表单Field", "", formLayout);
//        addChild(root, formLayout);

        LayoutProperty formNameProp = new LayoutProperty(R.layout.prop.form.NAME, "名称", null, formLayout);
        LayoutProperty formCNameProp = new LayoutProperty(FORM_FIELD_DISPLAY_NAME, "显示名", null, formLayout);
        LayoutProperty formTypeProp = new LayoutProperty(R.layout.prop.form.TYPE, "表单类型", null, formLayout);
        LayoutProperty formColCountProp = new LayoutProperty(R.layout.prop.form.COL_COUNT, "列数", "3", formLayout);
        LayoutProperty formColWidthProp = new LayoutProperty(R.layout.prop.form.COL_WIDTH, "列宽", "180", formLayout);
        LayoutProperty formLabalGap = new LayoutProperty(R.layout.prop.form.LABEL_GAP, "labelGap", "5", formLayout);
        LayoutProperty formFieldGap = new LayoutProperty(R.layout.prop.form.FIELD_GAP, "fieldGap", "15", formLayout);
        LayoutProperty formHgap = new LayoutProperty(R.layout.prop.form.HGAP, "hgap", "3", formLayout);
        LayoutProperty formVgap = new LayoutProperty(R.layout.prop.form.VGAP, "vgap", "5", formLayout);

        LayoutProperty formFieldNameProp = new LayoutProperty(FORM_FIELD_NAME, "名称", null, formFieldLayout);
        LayoutProperty formFieldDisplayNameProp = new LayoutProperty(R.layout.prop.form.field.DISPLAY_NAME, "显示名", null, formFieldLayout);
        LayoutProperty formFieldSingleLineProp = new LayoutProperty(R.layout.prop.form.field.IS_SINGLE_LINE, "独行", "false", formFieldLayout);
        LayoutProperty formFieldDisplayProp = new LayoutProperty(R.layout.prop.form.field.IS_DISPLAY, "显示", "true", formFieldLayout);
        LayoutProperty formFieldWidthProp = new LayoutProperty(R.layout.prop.form.field.WIDTH, "宽", "180", formFieldLayout);
        LayoutProperty formFieldHeghtProp = new LayoutProperty(R.layout.prop.form.field.HEIGHT, "高", null, formFieldLayout);
        LayoutProperty formFieldDisplayStyleProp = new LayoutProperty(R.layout.prop.form.field.DISPLAY_STYLE, "显示风格", "0", formFieldLayout);
        LayoutProperty formFieldSortNumProp = new LayoutProperty(R.layout.prop.form.field.SORT_NUM, "排序号", null, formFieldLayout);

        formLayout.getActionConfigs().add(new ActionConfig("form_save", "保存"));
        formLayout.getActionConfigs().add(new ActionConfig("form_exit", "退出"));

        return root;
    }

    private static void addChild(Layout parent, Layout child) {
        child.setSortNum(parent.getChildren().size() + 1);
        parent.getChildren().add(child);
    }
}
