<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
package ${project.packageName}.dao;

import ${project.packageName}.entity.${meta.name};
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public interface ${meta.name}Dao {
    void add(${meta.name} vo) throws Exception;

    void update(${meta.name} vo) throws Exception;

    void delete(String id) throws Exception;

    ${meta.name} findById(String id) throws Exception;

    List<${meta.name}> find(Map<String, Object> map) throws Exception;

    List<${meta.name}> listAll(@Param("page")int page, @Param("pageSize")int pageSize) throws Exception;

    int count(Map<String, Object> map) throws Exception;
}
