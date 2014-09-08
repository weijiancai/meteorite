package com.meteorite.core.util.ftl;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FtlEnumMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return arguments.get(0).toString();
    }
}
