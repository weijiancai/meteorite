package com.meteorite.core.ui.model;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.jaxb.AbstractXmlSerialization;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 布局信息
 *
 * @author wei_jc
 * @since  1.0.0
 */
@XmlRootElement(name = "Layout")
@XmlType(propOrder = {"id", "pid", "name", "displayName", "valid", "inputDate", "sortNum", "desc", "refId", "properties", "children"})
public class Layout extends AbstractXmlSerialization implements Cloneable {
    /** 布局ID */
    private String id;
    /** 父布局ID */
    private String pid;
    /** 布局名称 */
    private String name;
    /** 布局显示名 */
    private String displayName;
    /** 描述 */
    private String desc;
    /** 引用布局ID */
    private String refId;
    /** 是否有效 */
    private boolean isValid;
    /** 录入时间 */
    private Date inputDate;
    /** 排序号*/
    private int sortNum;

    private List<LayoutProperty> properties;
    private List<Layout> children;
    private Layout parent;
    private Layout refLayout;

    public Layout() {}

    public Layout(String id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
    }

    public Layout(String name, String displayName, String desc, Layout parent) {
        this.name = name;
        this.displayName = displayName;
        this.desc = desc;
        this.parent = parent;
        // 将子节点添加到父节点中
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlElement(name = "Property")
    @XmlElementWrapper(name = "Properties")
    public List<LayoutProperty> getProperties() {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        return properties;
    }

    public void setProperties(List<LayoutProperty> properties) {
        this.properties = properties;
    }

    @XmlElement(type = Layout.class, name = "Layout")
    @XmlElementWrapper(name = "children")
    public List<Layout> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<Layout> children) {
        this.children = children;
    }

    @XmlTransient
    public Layout getParent() {
        if (parent == null && UString.isNotEmpty(pid)) {
            parent = LayoutManager.getLayoutById(pid);
        }
        return parent;
    }

    public void setParent(Layout parent) {
        this.parent = parent;
    }

    @XmlTransient
    public Layout getRefLayout() {
        return refLayout;
    }

    public void setRefLayout(Layout refLayout) {
        this.refLayout = refLayout;
    }

    public LayoutProperty getProperty(String propName) {
        for (LayoutProperty property : getProperties()) {
            if (propName.equals(property.getName())) {
                return property;
            }
        }

        return null;
    }

    public void addProperty(LayoutProperty property) {
        property.setLayout(this);
        getProperties().add(property);
    }

    @Override
    protected File getXmlFile() {
        return new File(SystemConfig.DIR_CLASS_PATH, SystemConfig.FILE_NAME_LAYOUT_CONFIG);
    }

    @Override
    public String toString() {
        return "Layout{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", desc='" + desc + '\'' +
                ", sortNum=" + sortNum +
                ", properties=" + properties +
                ", children=" + children +
                ", parent=" + parent +
                '}';
    }
}
