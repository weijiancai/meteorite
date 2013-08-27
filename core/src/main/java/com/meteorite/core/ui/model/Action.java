package com.meteorite.core.ui.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"name", "cname", "sortNum"})
public class Action {
    private String name;
    private String cname;
    private int sortNum;

    public Action() {}

    public Action(String name, String cname, int sortNum) {
        this.name = name;
        this.cname = cname;
        this.sortNum = sortNum;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
