package com.meteorite.core.codegen;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.tpl.CodeTpl;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.ftl.FreeMarkerConfiguration;
import com.meteorite.core.util.ftl.FreeMarkerTemplateUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private ProjectDefine project;
    private List<CodeTpl> codeTpls;
    private Meta meta;
    private Map<String, Object> map = new HashMap<String, Object>();

    public CodeGen(String baseDir) {
            this.baseDir = baseDir;
    }

    public CodeGen(ProjectDefine project, Meta meta) {
        this(project, project.getCodeTpls(), meta);
    }

    public CodeGen(ProjectDefine project, List<CodeTpl> codeTpls, Meta meta) {
        this.project = project;
        this.codeTpls = codeTpls;
        this.meta = meta;

        map.put("project", project);
        map.put("meta", meta);
        map.put("queryForm", ViewManager.getViewByName(meta.getName() + "QueryView").getQueryForm().toHtml(true));
        View editFormView = ViewManager.getViewByName(meta.getName() + "FormView");
        map.put("editForm", editFormView.getQueryForm().toHtml(true));
        map.put("editFormConfig", editFormView.getQueryForm());
    }

    public void gen() {
        try {
            StringTemplateLoader loader = new StringTemplateLoader();
            Configuration config = new Configuration();
            config.setTemplateLoader(loader);

            for (CodeTpl tpl : codeTpls) {
                String tplContent = tpl.getTplContent();
                if (UString.isNotEmpty(tpl.getTplFile())) {
                    tplContent = UFile.readString(new File(tpl.getTplFile()));
                }
                loader.putTemplate(tpl.getName(), tplContent);
                String str = FreeMarkerTemplateUtils.processTemplateIntoString(config.getTemplate(tpl.getName()), map);
                String fileName = tpl.getFileName().replace("${meta.name}", meta.getName());
                UFile.write(str, new File(tpl.getFilePath(), fileName).getAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("生成代码失败！", e);
        }
    }

    public void setBasePageDir(String basePageDir) {
        this.basePageDir = basePageDir;
    }

    public void genSpringCode(ProjectDefine project, Meta meta, String prefix) throws IOException, TemplateException {
        // 生成实体
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("meta", meta);
        map.put("packageName", "entity");
        map.put("queryForm", ViewManager.getViewByName(prefix + meta.getName() + "QueryView").getQueryForm().toHtml(true));
        String str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("Entity.ftl"), map);
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
        // 生成Controller类
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("Controller.ftl"), map);
        UFile.write(str, new File(baseDir, "controller/" + meta.getName() + "Controller.java").getAbsolutePath());
        // 生成jsp list页面
        str = FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.classPath().getTemplate("pageList.ftl"), map);
        UFile.write(str, new File(basePageDir, UString.firstCharToLower(meta.getName()) + "/" + UString.firstCharToLower(meta.getName()) + "List.jsp").getAbsolutePath());
    }
}
