<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
<#assign objectEqual = "com.meteorite.core.util.ftl.FtlObjectEqualMethod"?new()>
public class ${meta.name} {
<#list meta.fields as field>
    /** ${field.displayName} */
    private ${field.dataType.toJavaType()} ${field.name};
</#list>

<#list meta.fields as field>
    <#if objectEqual(field.dataType, "BOOLEAN") && field.name?starts_with("is")>
    public ${field.dataType.toJavaType()} ${field.name}() {
        return ${field.name};
    }

    public void set${field.name?substring(2)}(${field.dataType.toJavaType()} ${field.name}) {
        this.${field.name} = ${field.name};
    }
    <#else>
    public ${field.dataType.toJavaType()} get${field.name?cap_first}() {
        return ${field.name};
    }

    public void set${field.name?cap_first}(${field.dataType.toJavaType()} ${field.name}) {
        this.${field.name} = ${field.name};
    }
    </#if>

</#list>
}