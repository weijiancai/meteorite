<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
package ${project.packageName}.service.impl;

import ${project.packageName}.entity.${meta.name};
import ${project.packageName}.service.${meta.name}Service;
import ${project.packageName}.dao.${meta.name}Dao;
import com.wlb.util.UtilObject;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wei_jc
 * @since 1.0.0
 */
@Service
@Transactional
public class ${meta.name}ServiceImpl implements ${meta.name}Service {
    @Autowired
    private ${meta.name}Dao ${meta.name?uncap_first}Dao;

    public void add(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Dao.add(vo);
    }

    public void update(${meta.name} vo) throws Exception {
        ${meta.name?uncap_first}Dao.update(vo);
    }

    public void delete(String id) throws Exception {
        ${meta.name?uncap_first}Dao.delete(id);
    }

    public ${meta.name} findById(String id) throws Exception {
        return ${meta.name?uncap_first}Dao.findById(id);
    }

    public List<${meta.name}> find(${meta.name} vo, int page, int pageSize) throws Exception {
        Map<String, Object> map = UtilObject.toMap(vo);
        map.put("page", page);
        map.put("pageSize", pageSize);
        return ${meta.name?uncap_first}Dao.find(map);
    }

    public List<${meta.name}> listAll(int page, int pageSize) throws Exception {
        return ${meta.name?uncap_first}Dao.listAll(page, pageSize);
    }

    public int count(${meta.name} vo) throws Exception {
        Map<String, Object> map = UtilObject.toMap(vo);
        return ${meta.name?uncap_first}Dao.count(map);
    }
}
