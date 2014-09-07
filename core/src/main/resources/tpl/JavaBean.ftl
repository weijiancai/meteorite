<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
public class ${meta.name} {
<#list meta.fields as field>
    /** ${field.displayName} */
    private ${field.dataType.toJavaType()} ${field.name};
</#list>

<#list meta.fields as field>
    public ${field.dataType.toJavaType()} get${field.name?cap_first}() {
        return ${field.name};
    }

    public void set${field.name?cap_first}(${field.dataType.toJavaType()} ${field.name}) {
        this.${field.name} = ${field.name};
    }
</#list>
}