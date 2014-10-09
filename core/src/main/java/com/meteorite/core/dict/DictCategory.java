package com.meteorite.core.dict;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String description;
    private String pid;
    private boolean isValid;
    private boolean isSystem;
    private int sortNum;
    private Date inputDate;

    private DictCategory parent;
    private List<DictCategory> children;
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

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    @Transient
    public DictCategory getParent() {
        return parent;
    }

    public void setParent(DictCategory parent) {
        this.parent = parent;
        this.pid = parent.getPid();
    }

    @XmlElement(name = "DictCategory")
    public List<DictCategory> getChildren() {
        if (children == null) {
            children = new ArrayList<DictCategory>();
        }
        return children;
    }

    public void setChildren(List<DictCategory> children) {
        this.children = children;
    }

    @XmlElement(name = "DictCode")
    public List<DictCode> getCodeList() {
        if (codeList == null) {
            codeList = new ArrayList<DictCode>();
        }
        return codeList;
    }

    public void setCodeList(List<DictCode> codeList) {
        this.codeList = codeList;
        for (DictCode code : codeList) {
            code.setCategory(this);
        }
    }

    public String getCodeName(String codeValue) {
        for (DictCode code : codeList) {
            if (codeValue.equals(code.getDisplayName())) {
                return code.getName();
            }
        }

        return "";
    }

    public DictCode getDictCode(String codeId) {
        for (DictCode code : codeList) {
            if (code.getId().equals(codeId)) {
                return code;
            }
        }

        return null;
    }

    public DictCode getDictCodeByName(String codeName) {
        for (DictCode code : codeList) {
            if (code.getName().equals(codeName)) {
                return code;
            }
        }

        return null;
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
