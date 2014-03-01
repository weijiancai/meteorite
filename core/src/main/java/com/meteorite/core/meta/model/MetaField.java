package com.meteorite.core.meta.model;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * 元数据字段信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "dataType", "value", "defaultValue", "dictId", "valid", "sortNum", "inputDate", "desc"})
@MetaElement(displayName = "元数据字段")
public class MetaField {
    private long id;
    private String name;
    private String displayName;
    private MetaDataType dataType;
    private String desc;
    private String defaultValue;
    private String value;
    private String dictId;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private Meta meta;

    public MetaField() {}

    public MetaField(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public MetaField(String name, String displayName, String dictId) {
        this.name = name;
        this.displayName = displayName;
        this.dictId = dictId;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "ID", dataType = MetaDataType.NUMBER)
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @XmlAttribute
    @MetaFieldElement(displayName = "数据字典")
    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "数据类型")
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "描述")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "默认值")
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "字段值")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "是否有效")
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

    @XmlTransient
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
