package com.meteorite.core.project;

import com.alibaba.fastjson.JSON;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目定义
 *
 * @author wei_jc
 * @since 1.0.0
 */
@MetaElement(displayName = "项目定义")
public class ProjectDefine {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private String packageName;
    private String projectUrl;
    private Date inputDate;
    private boolean isValid;
    private int sortNum;

    private List<NavMenu> navMenus;
    private NavMenu rootNavMenu;

    @MetaFieldElement(displayName = "项目ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @MetaFieldElement(displayName = "项目名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "显示名")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @MetaFieldElement(displayName = "项目描述")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @MetaFieldElement(displayName = "包名")
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @MetaFieldElement(displayName = "录入时间")
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @MetaFieldElement(displayName = "是否有效")
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @MetaFieldElement(displayName = "排序号")
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @MetaFieldElement(displayName = "项目URL")
    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public List<NavMenu> getNavMenus() {
        if (navMenus == null) {
            navMenus = new ArrayList<NavMenu>();
        }
        return navMenus;
    }

    public void setNavMenus(List<NavMenu> navMenus) {
        this.navMenus = navMenus;
    }

    public List<NavMenu> getNavMenusByLevel(int level) {
        List<NavMenu> result = new ArrayList<NavMenu>();
        for (NavMenu navMenu : getNavMenus()) {
            if (navMenu.getLevel() == level) {
                result.add(navMenu);
            }
        }
        return result;
    }

    public NavMenu getRootNavMenu() {
        return rootNavMenu;
    }

    public void setRootNavMenu(NavMenu rootNavMenu) {
        this.rootNavMenu = rootNavMenu;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
