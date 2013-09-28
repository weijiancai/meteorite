package com.meteorite.core.ui.config;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.config.layout.FormConfig;

/**
 * 初始化配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ConfigInit {
    public static IViewConfig getProjectConfig() {
        IViewConfig viewConfig = ViewConfigFactory.createFormConfig(MetaManager.getMeta(ProjectConfig.class));
        FormConfig formConfig = new FormConfig(viewConfig.getLayoutConfig());
        formConfig.setColCount(1);
        return viewConfig;
    }
}
