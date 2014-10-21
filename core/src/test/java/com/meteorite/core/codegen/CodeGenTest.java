package com.meteorite.core.codegen;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.ProjectManager;
import com.meteorite.project.wlb.ProjectDefineInit;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CodeGenTest {
    @BeforeClass
    public static void setup() throws Exception {
        SystemManager.getInstance().init();
    }
    @Test
    public void testGenSpringCode() throws Exception {

        /*Meta meta = MetaManager.getMeta("CmsDbNews");
        meta.setName("News");
        CodeGen codeGen = new CodeGen("D:\\workspace_other\\wlb\\src\\com\\wlb");
        codeGen.setBasePageDir("D:\\workspace_other\\wlb\\WebContent\\WEB-INF\\html");
        ProjectDefine project = ProjectManager.getProjectByName(ProjectDefineInit.PROJECT_NAME);
        codeGen.genSpringCode(project, meta, "CmsDb");*/

        /*Meta meta = MetaManager.getMeta("BsDzCodeClass");
        meta.setName("CodeClass");
        CodeGen codeGen = new CodeGen("D:\\workspace_other\\wlb\\src\\com\\wlb");
        codeGen.setBasePageDir("D:\\workspace_other\\wlb\\WebContent\\WEB-INF\\html");
        ProjectDefine project = ProjectManager.getProjectByName(ProjectDefineInit.PROJECT_NAME);
        codeGen.genSpringCode(project, meta, "BsDz");*/

        Meta meta = MetaManager.getMeta("BsDzCodeGlobal");
        meta.setName("CodeGlobal");
        CodeGen codeGen = new CodeGen("D:\\workspace_other\\wlb\\src\\com\\wlb");
        codeGen.setBasePageDir("D:\\workspace_other\\wlb\\WebContent\\WEB-INF\\html");
        ProjectDefine project = ProjectManager.getProjectByName(ProjectDefineInit.PROJECT_NAME);
        codeGen.genSpringCode(project, meta, "BsDz");
    }

    @Test
    public void testGenProjectCode() throws Exception {
        ProjectDefine project = ProjectManager.getProjectByName("XinJu");
        CodeGen codeGen = new CodeGen(project, MetaManager.getMeta("News"));
        codeGen.gen();
    }
}