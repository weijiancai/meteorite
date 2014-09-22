package com.meteorite.core.codegen;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.ftl.FreeMarkerConfiguration;
import com.meteorite.core.util.ftl.FreeMarkerTemplateUtils;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成代码
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class CodeGen {
    private String baseDir;
    private String basePageDir;

    public CodeGen(String baseDir) {
            this.baseDir = baseDir;
    }

    public String getBasePageDir() {
        return basePageDir;
    }

    public void setBasePageDir(String basePageDir) {
        this.basePageDir = basePageDir;
    }

    public void genSpringCode(ProjectDefine project, Meta meta) throws IOException, TemplateException {
        // 生成实体
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("meta", meta);
        map.put("packageName", "entity");
        String str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("JavaBean.ftl"), map);
        UFile.write(str, new File(baseDir, "entity/" + meta.getName() + ".java").getAbsolutePath());
        // 生成Dao接口
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("DaoInterface.ftl"), map);
        UFile.write(str, new File(baseDir, "dao/" + meta.getName() + "Dao.java").getAbsolutePath());
        // 生成MyBatis Mapper
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("mapper.ftl"), map);
        UFile.write(str, new File(baseDir, "dao/" + meta.getName() + "Mapper.xml").getAbsolutePath());
        // 生成Service接口
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("ServiceInterface.ftl"), map);
        UFile.write(str, new File(baseDir, "service/" + meta.getName() + "Service.java").getAbsolutePath());
        // 生成Service接口实现类
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("ServiceImpl.ftl"), map);
        UFile.write(str, new File(baseDir, "service/impl/" + meta.getName() + "ServiceImpl.java").getAbsolutePath());
    }
}
