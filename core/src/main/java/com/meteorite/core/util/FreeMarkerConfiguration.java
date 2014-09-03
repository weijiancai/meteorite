package com.meteorite.core.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker 配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FreeMarkerConfiguration extends Configuration {
    private static final FreeMarkerConfiguration instance = new FreeMarkerConfiguration();
    private static Map<String, Template> tplCache = new HashMap<String, Template>();

    public FreeMarkerConfiguration() {
        super();
        this.setTemplateLoader(new ClassTemplateLoader(getClass(), "/tpl"));

    }

    public static FreeMarkerConfiguration getInstance() {
        return instance;
    }

    /*public Template getTemplate(String path) {
        Template template = tplCache.get(path);
        if (template == null) {
            template = instance.getTemplate(path);
            tplCache.put(path, template);
        }

        return template;
    }*/
}
