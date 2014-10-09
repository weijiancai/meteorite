package com.meteorite.core.meta.model;

import com.meteorite.core.meta.MetaDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 元数据项
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class MetaItem {
    /** 数据项ID */
    private String id;
    /** 名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 数据类型 */
    private MetaDataType dataType;
    /** 分类 */
    private String category;
    /** 最大长度 */
    private int maxLength;
    /** 描述 */
    private String description;
    /** 是否有效 */
    private boolean isValid;
    /** 排序号 */
    private int sortNum;
    /** 录入时间 */
    private Date inputDate;

    public MetaItem() {
    }

    public MetaItem(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlAttribute
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    @XmlAttribute
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlAttribute
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

}
