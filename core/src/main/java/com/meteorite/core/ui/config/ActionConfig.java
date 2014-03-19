package com.meteorite.core.ui.config;

import com.meteorite.core.ui.IActionConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Action配置
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement(name = "Action")
@XmlType(propOrder = {"id", "name", "displayName", "sortNum"})
public class ActionConfig implements IActionConfig {
    private String id;
    private String name;
    private String displayName;
    private int sortNum;

    public ActionConfig() {}

    public ActionConfig(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    @XmlAttribute
    public String getId() {
        return id;
    }

    @Override
    @XmlAttribute
    public String getName() {
        return name;
    }

    @Override
    @XmlAttribute
    public String getDisplayName() {
        return displayName;
    }

    @Override
    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    @Override
    public IActionConfig clone() {
        try {
            return (IActionConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
