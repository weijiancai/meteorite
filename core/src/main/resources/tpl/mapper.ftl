<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${project.packageName}.dao.${meta.name}Dao">
    <select id="find" resultClass="${meta.name}">
        SELECT * FROM ${meta.resource.name}
        <dynamic prepend="WHERE ">
        <#list meta.fields as field>
            <isNotEmpty property="${field.name}">
                ${field.originalName} = #${field.name}#
            </isNotEmpty>
        </#list>
        </dynamic>
    </select>

    <select id="findById" resultClass="${project.packageName}.entity.${meta.name}">
        SELECT * FROM ${meta.resource.name}
        <dynamic prepend="WHERE ">
        <#list meta.pkFields as field>
            <isNotEmpty property="${field.name}">
            ${field.originalName} = #${field.name}#
            </isNotEmpty>
        </#list>
        </dynamic>
    </select>

    <update id="update" parameterType="${project.packageName}.entity.${meta.name}">
        update ${meta.resource.name}
        <set>
        <#list meta.fields as field>
            <#if !field.pk>
                <if test="${field.name} != null">
                    ${field.name} = ${"#"}{${field.name}},
                </if>
            </#if>
        </#list>
        </set>
        <dynamic prepend="WHERE ">
        <#list meta.pkFields as field>
            <isNotEmpty property="${field.name}">
            ${field.originalName} = #${field.name}#
            </isNotEmpty>
        </#list>
        </dynamic>
    </update>

    <insert id="add" parameterType="${project.packageName}.entity.${meta.name}">
        insert into ${meta.resource.name}(
        <#list meta.fields as field>
            ${field.originalName}
            <#if field_index < meta.fields?size - 1>
                ,
            </#if>
        </#list>
        ) values(
        <#list meta.fields as field>
            ${"#"}{${field.name}}
            <#if field_index < meta.fields?size - 1>
                ,
            </#if>
        </#list>
        )
    </insert>

    <delete id="delete" parameterType="java.lang.String">
        delete from ${meta.resource.name}
        <dynamic prepend="WHERE ">
        <#list meta.pkFields as field>
            <isNotEmpty property="${field.name}">
            ${field.originalName} = #${field.name}#
            </isNotEmpty>
        </#list>
        </dynamic>
    </delete>
</mapper>