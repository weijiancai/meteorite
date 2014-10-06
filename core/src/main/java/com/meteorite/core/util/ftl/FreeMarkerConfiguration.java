package com.meteorite.core.util.ftl;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;

import javax.servlet.ServletContext;

/**
 * FreeMarker 配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FreeMarkerConfiguration {
    private static Configuration classPathConfig;
    private static Configuration webappConfig;

    private static ServletContext servletContext;

    private FreeMarkerConfiguration() {}

    public static void setServletContext(ServletContext servletContext) {
        FreeMarkerConfiguration.servletContext = servletContext;
    }

    public static Configuration classPath() {
        if (classPathConfig == null) {
            classPathConfig = new Configuration();
            classPathConfig.setTemplateLoader(new ClassTemplateLoader(FreeMarkerConfiguration.class, "/tpl"));
        }
        return classPathConfig;
    }

    public static Configuration webapp() {
        if (webappConfig == null) {
            webappConfig = new Configuration();
            webappConfig.setTemplateLoader(new WebappTemplateLoader(servletContext));
        }
        return webappConfig;
    }
}
