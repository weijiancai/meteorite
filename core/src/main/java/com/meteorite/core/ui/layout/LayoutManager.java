package com.meteorite.core.ui.layout;

import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.ui.layout.model.Layout;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutManager {
    private static Layout root = ProjectConfigFactory.getProjectConfig().getLayout();
    private static Map<String, Layout> cache = new HashMap<String, Layout>();

    static {
        iterator(root);
    }

    static void iterator(Layout layout) {
        cache.put(layout.getName(), layout);
        if(layout.getChildren() != null) {
            for(Layout lt : layout.getChildren()) {
                iterator(lt);
            }
        }
    }

    public static void load() throws Exception {

    }

    public static Layout getLayout(String layoutName) {
        return cache.get(layoutName);
    }
}
