package com.meteorite.core.util.ftl;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FtlObjectEqualMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return arguments.get(0).toString().equals(arguments.get(1).toString());
    }
}
