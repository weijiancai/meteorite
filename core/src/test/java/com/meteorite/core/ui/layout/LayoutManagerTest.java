package com.meteorite.core.ui.layout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class LayoutManagerTest {
    @Test
    public void testToJson() {
        LayoutConfigManager.load();
        ILayoutConfig layoutConfig = LayoutConfigManager.createFormLayout(MetaManager.getMeta(DataSource.class));
        FormConfig formConfig = new FormConfig(layoutConfig);
        String jsonStr = JSON.toJSONString(formConfig, SerializerFeature.PrettyFormat);
        System.out.println(jsonStr);
    }
}
