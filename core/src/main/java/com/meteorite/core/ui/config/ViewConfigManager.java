package com.meteorite.core.ui.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ViewConfigManager {
    private static Map<String, ViewConfig> cache = new HashMap<String, ViewConfig>();

    public static ViewConfig getViewConfig(String viewName) {
        return cache.get(viewName);
    }

    public static void addViewConfig(ViewConfig viewConfig) {
        cache.put(viewConfig.getName(), viewConfig);
    }
}
