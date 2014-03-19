package com.meteorite.core.dict;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 数据字典分类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class DictCategory implements Serializable {
    private String id;
    private String name;
    private String desc;
    private boolean isValid;
    private boolean isSystem;
    private int sortNum;
    private Date inputDate;

    private List<DictCode> codeList;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @XmlAttribute
    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
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

    @XmlElementWrapper(name = "codes")
    @XmlElement(name = "DictCode")
    public List<DictCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<DictCode> codeList) {
        this.codeList = codeList;
    }

    public String getCodeName(String codeValue) {
        for (DictCode code : codeList) {
            if (codeValue.equals(code.getDisplayName())) {
                return code.getName();
            }
        }

        return "";
    }

    @Override
    public String toString() {
        return "DictCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isSystem=" + isSystem +
                ", sortNum=" + sortNum +
                ", codeList=" + codeList +
                '}';
    }
}
