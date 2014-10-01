<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
package ${project.packageName}.controller;

import ${project.packageName}.entity.${meta.name};
import ${project.packageName}.service.${meta.name}Service;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/${meta.name?uncap_first}")
public class ${meta.name}Controller {
    @Autowired
    private ${meta.name}Service ${meta.name?uncap_first}Service;

    @RequestMapping(value = "/add")
    @ResponseBody
    public void add(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Service.add(vo);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public void update(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Service.update(vo);
    }

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest req) throws Exception {
        List<${meta.name}> list = ${meta.name?uncap_first}Service.listAll();
        req.setAttribute("list", list);
        return "${meta.name?uncap_first}/${meta.name?uncap_first}List";
    }
}
