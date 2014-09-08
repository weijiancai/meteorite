<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
public class ${meta.name} {
<#list meta.fields as field>
    /** ${field.displayName} */
    private ${field.dataType.toJavaType()} ${field.name};
</#list>

<#list meta.fields as field>
    <#assign isBoolean = field.dataType.name />
    <#if isBoolean == "boolean">
    public ${field.dataType.toJavaType()} get${field.name.substring(2)}() {
        return ${field.name};
    }

    public void set${field.name.substring(2)} ${field.dataType.toJavaType()} ${field.name}) {
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