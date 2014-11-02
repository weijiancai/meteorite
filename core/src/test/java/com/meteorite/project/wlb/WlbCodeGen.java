package com.meteorite.project.wlb;

import com.meteorite.core.codegen.CodeGen;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.ProjectManager;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class WlbCodeGen {
    public static void main(String[] args) throws Exception {
        SystemManager.getInstance().init();

        ProjectDefine project = ProjectManager.getProjectByName("XinJu");
        CodeGen codeGen = new CodeGen(project, MetaManager.getMeta("News"));
        codeGen.gen();
    }
}
