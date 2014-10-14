<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
package ${project.packageName}.controller;

import ${project.packageName}.entity.${meta.name};
import ${project.packageName}.service.${meta.name}Service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlb.util.Paging;

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

    @RequestMapping(value = "/go")
    public String goList(HttpServletRequest req) throws Exception {
        return "admin/${meta.name}";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public void list(HttpServletResponse response, ${meta.name} vo, int start, int length) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        int count = ${meta.name?uncap_first}Service.count(vo);
        List<${meta.name}> list = ${meta.name?uncap_first}Service.listAll(start, length);
        result.put("draw", 1);
        result.put("recordsTotal", count);
        result.put("recordsFiltered", count);
        result.put("data", list);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
