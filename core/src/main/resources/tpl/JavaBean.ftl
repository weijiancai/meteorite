<#-- @ftlvariable name="meta" type="com.meteorite.core.meta.model.Meta" -->
public class ${meta.name} {

<#list meta.fields as field>
    private ${field.dataType.toJavaType()} ${field.name};
</#list>


<#list meta.fields as field>
    public ${field.dataType.toJavaType()} ${field.name};
</#list>
    @MetaFieldElement(displayName = "项目ID")
    public String getId() {
    return id;
    }

    public void setId(String id) {
    this.id = id;
    }

    @MetaFieldElement(displayName = "项目名称")
    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    @MetaFieldElement(displayName = "显示名")
    public String getDisplayName() {
    return displayName;
    }

    public void setDisplayName(String displayName) {
    this.displayName = displayName;
    }

    @MetaFieldElement(displayName = "项目描述")
    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    @MetaFieldElement(displayName = "包名")
    public String getPackageName() {
    return packageName;
    }

    public void setPackageName(String packageName) {
    this.packageName = packageName;
    }

    @MetaFieldElement(displayName = "录入时间")
    public Date getInputDate() {
    return inputDate;
    }

    public void setInputDate(Date inputDate) {
    this.inputDate = inputDate;
    }

    @MetaFieldElement(displayName = "是否有效")
    public boolean isValid() {
    return isValid;
    }

    public void setValid(boolean valid) {
    isValid = valid;
    }

    @MetaFieldElement(displayName = "排序号")
    public int getSortNum() {
    return sortNum;
    }

    public void setSortNum(int sortNum) {
    this.sortNum = sortNum;
    }

    @MetaFieldElement(displayName = "项目URL")
    public String getProjectUrl() {
    return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
    this.projectUrl = projectUrl;
    }


    @Override
    public String toString() {
    return JSON.toJSONString(this, true);
}