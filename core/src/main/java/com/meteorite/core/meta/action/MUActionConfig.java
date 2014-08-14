package com.meteorite.core.meta.action;

import com.meteorite.core.meta.model.Meta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MUActionConfig {
    private static MUActionConfig instance = new MUActionConfig();
    private Map<String, MUAction> cache = new HashMap<String, MUAction>();

    public static MUActionConfig getInstance() {
        return instance;
    }

    private MUActionConfig() {
    }

    public void addAction(MUAction action) {
        String key = String.format("%s.%s", action.getMeta().getName(), action.getName());
        cache.put(key, action);
    }

    public MUAction getAction(Meta meta, String actionName) {
        String key = String.format("%s.%s", meta.getName(), actionName);
        return cache.get(key);
    }
}
