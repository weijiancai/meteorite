package com.meteorite.core.meta.model;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 元数据信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "valid", "sortNum", "inputDate", "desc", "fields"})
@MetaElement(displayName = "元数据")
public class Meta {
    private String id;
    private String name;
    private String displayName;
    private String desc;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private List<MetaField> fields = new ArrayList<>();

    public Meta() {}

    public Meta(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "ID", dataType = MetaDataType.NUMBER)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "显示名", sortNum = -1)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @MetaFieldElement(displayName = "描述")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    @MetaFieldElement(name = "valid", displayName = "是否有效", dictId = "com_meteorite_core_dict_EnumBoolean")
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "排序号")
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "录入时间")
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlElement(name = "MetaField")
    @XmlElementWrapper(name = "fields")
    public List<MetaField> getFields() {
        return fields;
    }

    public void setFields(List<MetaField> fields) {
        this.fields = fields;
    }

    public void setFieldValue(String fieldName, String value) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setValue(value);
                break;
            }
        }
    }

    public String getFieldValue(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getValue();
            }
        }

        return null;
    }
}
