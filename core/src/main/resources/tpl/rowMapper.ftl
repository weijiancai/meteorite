<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
<#assign objectEqual = "com.meteorite.core.util.ftl.FtlObjectEqualMethod"?new()>
public static RowMapper<${meta.name}> get${meta.name}() {
    return new RowMapper<${meta.name}>() {
        @Override
        public ${meta.name} mapRow(ResultSet rs) throws SQLException {
            ${meta.name} ${meta.name?uncap_first} = new ${meta.name}();

    <#list meta.fields as field>
        <#assign originalName = field.originalName?substring(field.originalName?index_of(".") + 1)>
        <#if objectEqual(field.dataType, "BOOLEAN") && field.name?starts_with("is")>
            ${meta.name?uncap_first}.set${field.name?substring(2)}("T".equals(rs.getString("${originalName}")));
        <#elseif objectEqual(field.dataType, "INTEGER")>
            ${meta.name?uncap_first}.set${field.name?cap_first}(rs.getInt("${originalName}"));
        <#elseif objectEqual(field.dataType, "DATE")>
            ${meta.name?uncap_first}.set${field.name?cap_first}(rs.getDate("${originalName}"));
        <#else>
            ${meta.name?uncap_first}.set${field.name?cap_first}(rs.getString("${originalName}"));
        </#if>
    </#list>

            return ${meta.name?uncap_first};
        }
    };
}