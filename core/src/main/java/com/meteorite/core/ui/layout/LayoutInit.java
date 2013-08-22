package com.meteorite.core.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.ui.layout.model.Layout;
import com.meteorite.core.ui.layout.model.LayoutProperty;

/**
 * 初始化布局
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutInit {
    /**
     * 初始化布局，返回根布局管理器
     *
     * @return 返回根布局管理器
     */
    public static Layout getRoot() {
        Layout root = new Layout(R.layout.ROOT, "布局管理器", "", null);
        Layout formLayout = new Layout(R.layout.FORM, "表单", "", root);
        Layout formLabelLayout = new Layout(R.layout.FORM_LABEL, "表单Label", "", formLayout);
        Layout formFieldLayout = new Layout(R.layout.FORM_FIELD, "表单Field", "", formLayout);

        LayoutProperty formNameProp = new LayoutProperty(R.layout.prop.form.NAME, "名称", null, formLayout);
        LayoutProperty formCNameProp = new LayoutProperty(R.layout.prop.form.CNAME, "中文名", null, formLayout);
        LayoutProperty formTypeProp = new LayoutProperty(R.layout.prop.form.TYPE, "表单类型", null, formLayout);
        LayoutProperty formColCountProp = new LayoutProperty(R.layout.prop.form.COL_COUNT, "列数", "3", formLayout);
        LayoutProperty formColWidthProp = new LayoutProperty(R.layout.prop.form.COL_WIDTH, "列宽", "180", formLayout);
        LayoutProperty formLabalGap = new LayoutProperty(R.layout.prop.form.LABEL_GAP, "labelGap", "5", formLayout);
        LayoutProperty formFieldGap = new LayoutProperty(R.layout.prop.form.FIELD_GAP, "fieldGap", "15", formLayout);
        LayoutProperty formHgap = new LayoutProperty(R.layout.prop.form.HGAP, "hgap", "3", formLayout);
        LayoutProperty formVgap = new LayoutProperty(R.layout.prop.form.VGAP, "vgap", "5", formLayout);

        LayoutProperty formFieldDisplayNameProp = new LayoutProperty(R.layout.prop.form.field.DISPLAY_NAME, "显示名", null, formFieldLayout);
        LayoutProperty formFieldSingleLineProp = new LayoutProperty(R.layout.prop.form.field.IS_SINGLE_LINE, "独行", null, formFieldLayout);
        LayoutProperty formFieldDisplayProp = new LayoutProperty(R.layout.prop.form.field.IS_DISPLAY, "显示", null, formFieldLayout);
        LayoutProperty formFieldWidthProp = new LayoutProperty(R.layout.prop.form.field.WIDTH, "宽", null, formFieldLayout);
        LayoutProperty formFieldHeghtProp = new LayoutProperty(R.layout.prop.form.field.HEIGHT, "高", null, formFieldLayout);
        LayoutProperty formFieldDisplayStyleProp = new LayoutProperty(R.layout.prop.form.field.DISPLAY_STYLE, "显示风格", null, formFieldLayout);
        LayoutProperty formFieldSortNumProp = new LayoutProperty(R.layout.prop.form.field.SORT_NUM, "排序号", null, formFieldLayout);

        return root;
    }
}
