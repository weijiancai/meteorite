<#-- @ftlvariable name="page" type="int" -->
<#-- @ftlvariable name="project" type="com.meteorite.core.project.ProjectDefine" -->
<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${project.packageName}.dao.${meta.name}Dao">
    <select id="find" resultType="${project.packageName}.entity.${meta.name}">
        SELECT * FROM ${meta.resource.name}
        <where>
        <#list meta.fields as field>
            <if test="${field.name} != null">
                and ${field.originalName} = #${field.name}#
            </if>
        </#list>
        </where>
        limit ${"#"}{page, jdbcType=INTEGER} , ${"#"}{pageSize, jdbcType=INTEGER}
    </select>

    <select id="findById" resultType="${project.packageName}.entity.${meta.name}">
        SELECT * FROM ${meta.resource.name}
        <where>
        <#list meta.pkFields as field>
            <if test="${field.name} != null">
                and ${field.originalName} = #${field.name}#
            </if>
        </#list>
        </where>
    </select>

    <select id="listAll" resultType="${project.packageName}.entity.${meta.name}">
        SELECT * FROM ${meta.resource.name} limit ${"#"}{page, jdbcType=INTEGER} , ${"#"}{pageSize, jdbcType=INTEGER}
    </select>

    <update id="update" parameterType="${project.packageName}.entity.${meta.name}">
        update ${meta.resource.name}
        <set>
        <#list meta.fields as field>
            <#if !field.pk>
                <if test="${field.name} != null">
                    ${field.name} = ${"#"}{${field.name}}<#if field_index < meta.fields?size - 1>,</#if>
                </if>
            </#if>
        </#list>
        </set>
        <where>
        <#list meta.pkFields as field>
            <if test="${field.name} != null">
                and ${field.originalName} = #${field.name}#
            </if>
        </#list>
        </where>
    </update>

    <insert id="add" parameterType="${project.packageName}.entity.${meta.name}">
        insert into ${meta.resource.name}(
        <#list meta.fields as field>
            ${field.originalName}<#if field_index < meta.fields?size - 1>,</#if>
        </#list>
        ) values(
        <#list meta.fields as field>
            ${"#"}{${field.name}}<#if field_index < meta.fields?size - 1>,</#if>
        </#list>
        )
    </insert>

    <delete id="delete" parameterType="java.lang.String">
        delete from ${meta.resource.name}
        <where>
        <#list meta.pkFields as field>
            <if test="${field.name} != null">
                and ${field.originalName} = #${field.name}#
            </if>
        </#list>
        </where>
    </delete>

    <select id="count" resultType="int">
        SELECT count(1) FROM ${meta.resource.name}
    </select>
</mapper>