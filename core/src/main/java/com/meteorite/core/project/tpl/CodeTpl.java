package com.meteorite.core.project.tpl;

import java.util.Date;

/**
 * 代码模板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class CodeTpl {
    /** 模板ID */
    private String id;
    /** 模板名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 描述 */
    private String description;
    /** 项目ID */
    private String projectId;
    /** 文件名 */
    private String fileName;
    /** 文件路径 */
    private String filePath;
    /** 模板内容 */
    private String tplContent;
    /** 是否有效 */
    private boolean isValid;
    /** 排序号 */
    private int sortNum;
    /** 录入时间 */
    private Date inputDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

}
