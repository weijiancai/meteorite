package com.meteorite.core.meta.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.object.DBColumn;
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
 * @since 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "dataType", "value", "defaultValue", "dictId", "valid", "sortNum", "inputDate", "description"})
@MetaElement(displayName = "元数据字段")
public class MetaField {
    private String id;
    private String name;
    private String displayName;
    private MetaDataType dataType;
    private String description;
    private String defaultValue;
    private String value;
    private String dictId;
    private String originalName; // 原名称，如数据库列名等
    private int maxLength;
    private boolean isPk;
    private boolean isFk;
    private boolean isRequire;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private Meta meta;
    private MetaField refField; // 引用字段

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
    @MetaFieldElement(displayName = "ID", sortNum = 0)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称", sortNum = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "显示名", sortNum = 20)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "数据字典", dataType=MetaDataType.DICT, dictId = "ROOT", sortNum = 30)
    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "数据类型", dataType=MetaDataType.DICT, dictId = "MetaDataType", sortNum = 40)
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "原名称", sortNum = 50)
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "最大长度", sortNum = 60)
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "是否主键", sortNum = 70)
    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "是否外键", sortNum = 80)
    public boolean isFk() {
        return isFk;
    }

    public void setFk(boolean isFk) {
        this.isFk = isFk;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "是否必须", sortNum = 90)
    public boolean isRequire() {
        return isRequire;
    }

    public void setRequire(boolean isRequire) {
        this.isRequire = isRequire;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "描述", sortNum = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "默认值", sortNum = 110)
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "字段值", sortNum = 120)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "是否有效", sortNum = 130)
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "排序号", sortNum = 140)
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "录入时间", dataType = MetaDataType.DATE, sortNum = 150)
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @XmlTransient
    public MetaField getRefField() {
        return refField;
    }

    public void setRefField(MetaField refField) {
        this.refField = refField;
    }
}
