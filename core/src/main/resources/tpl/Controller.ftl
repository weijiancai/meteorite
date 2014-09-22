<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
package ${project.packageName}.controller;

import ${project.packageName}.entity.${meta.name};
import ${project.packageName}.service.${meta.name}Service;
import net.sf.json.JSONObject;
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
@RequestMapping(value = "/${meta.name}")
public class ${meta.name}Controller {
    @Autowired
    private ${meta.name}Service ${meta.name?uncap_first}Service;

    @RequestMapping(value = "/save")
    @ResponseBody
    public void save(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Service.save(vo);
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public void save(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Service.update(vo);
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public void list(HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        List<ForumPost> list = forumPostService.listAll();
        result.put("total", list.size());
        result.put("rows", list);

        response.setContentType("application/json;charset=UTF-8");
        JSONObject json = JSONObject.fromObject(result);
        response.getWriter().write(json.toString());
    }

    @RequestMapping(value = "/look")
    public String look(HttpServletRequest request, int postId) throws Exception {
        ForumPost result = forumPostService.getForumPost(postId);
        request.setAttribute("forumPost", result);
        return "forum_post_look";
    }
}
