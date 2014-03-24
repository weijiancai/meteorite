package com.meteorite.core.ui.layout;

import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.ui.model.Layout;
import com.meteorite.core.ui.model.LayoutProperty;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutInit {
    private JdbcTemplate template;

    public LayoutInit(JdbcTemplate template) {
        this.template = template;
        init();
    }

    private void init() {
        Layout root = new Layout("ROOT", "布局管理器", "", null);

        Layout form = new Layout("FORM", "表单", "", root);
        form.addProperty(new LayoutProperty("name", "名称", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("displayName", "显示名", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("formType", "表单类型", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("colCount", "列数", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("colWidth", "列宽", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("labelGap", "labelGap", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("fieldGap", "fieldGap", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("hgap", "hgap", "", PropertyType.MP, ""));
        form.addProperty(new LayoutProperty("vgap", "vgap", "", PropertyType.MP, ""));

        form.addProperty(new LayoutProperty("name", "名称", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("displayName", "显示名", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("isSingleLine", "独行", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("isDisplay", "显示", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("width", "宽", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("height", "高", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("displayStyle", "显示风格", "", PropertyType.IP, ""));
        form.addProperty(new LayoutProperty("dataType", "数据类型", "", PropertyType.IP, ""));
    }
}
