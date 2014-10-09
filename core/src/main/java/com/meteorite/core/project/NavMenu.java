package com.meteorite.core.project;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/**
 * 导航菜单
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class NavMenu {
    /** 菜单ID */
    private String id;
    /** 菜单名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 图标 */
    private String icon;
    /** URL */
    private String url;
    /** 父菜单ID */
    private String pid;
    /** 菜单级别 */
    private int level;
    /** 项目ID */
    private String projectId;
    /** 是否有效 */
    private boolean isValid;
    /** 排序号 */
    private int sortNum;
    /** 录入时间 */
    private Date inputDate;

    private List<NavMenu> children;

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
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @XmlAttribute
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @XmlAttribute
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    @XmlElement(name = "NavMenu")
    public List<NavMenu> getChildren() {
        if (children == null) {
            children = new ArrayList<NavMenu>();
        }
        // 排序
        Collections.sort(children, new Comparator<NavMenu>() {
            @Override
            public int compare(NavMenu o1, NavMenu o2) {
                return o1.sortNum - o2.sortNum;
            }
        });
        return children;
    }

    public void setChildren(List<NavMenu> children) {
        this.children = children;
    }
}