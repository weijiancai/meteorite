<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
<#assign objectEqual = "com.meteorite.core.util.ftl.FtlObjectEqualMethod"?new()>
public static IPDB get${meta.name}(final ${meta.name} ${meta.name?uncap_first}) {
    return new IPDB() {
        @Override
        public Map<String, Map<String, Object>> getPDBMap() {
            Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

            Map<String, Object> map = new HashMap<String, Object>();

    <#list meta.fields as field>
        <#assign originalName = field.originalName?substring(field.originalName?index_of(".") + 1)>
        <#if originalName == "id">
            map.put("id", UString.getValue(${meta.name?uncap_first}.getId(), UUIDUtil.getUUID()));
        <#elseif objectEqual(field.dataType, "BOOLEAN") && field.name?starts_with("is")>
            map.put("${originalName}", ${meta.name?uncap_first}.${field.name}() ? "T" : "F");
        <#else>
             map.put("${originalName}", ${meta.name?uncap_first}.get${field.name?cap_first}());
        </#if>
    </#list>

            result.put("${meta.rsId?substring(meta.rsId?last_index_of("/") + 1)}", map);

            return result;
        }
    };
}