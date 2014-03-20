package com.meteorite.core.ui.layout;

import com.meteorite.core.ui.model.Layout;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutManagerTest {
    @Test
    public void testToJson() {
       /* LayoutManager.load();
        ILayoutConfig layoutConfig = LayoutManager.createFormLayout(MetaManager.getMeta(DBDataSource.class));
        FormConfig formConfig = new FormConfig(layoutConfig);
        String jsonStr = JSON.toJSONString(formConfig, SerializerFeature.PrettyFormat);
        System.out.println(jsonStr);*/
    }

    @Test
    public void testLoad() throws Exception {
        Layout layout = new Layout();
        /*layout.setId("A01");
        layout.setName("FORM");
        layout.setDisplayName("表单");
        List<Layout> children = layout.getChildren();
        children.add(new Layout("A02", "FORM_FIELD", "表单字段"));*/
        layout.load();
        System.out.println(layout);
        /*LayoutConfig config = JAXB.unmarshal(new File(SystemConfig.DIR_CLASS_PATH, SystemConfig.FILE_NAME_LAYOUT_CONFIG), LayoutConfig.class);
        System.out.println(config);*/
    }
}
