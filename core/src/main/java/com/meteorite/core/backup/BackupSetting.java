package com.meteorite.core.backup;

/**
 * 备份配置信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BackupSetting {
    private boolean isSetting;
    private boolean isDict;
    private boolean isProject;
    private boolean isMetaItem;
    private boolean isMeta;
    private boolean isMetaReference;
    private boolean isView;
    private boolean isDataSource;

    public boolean isSetting() {
        return isSetting;
    }

    public void setSetting(boolean isSetting) {
        this.isSetting = isSetting;
    }

    public boolean isDict() {
        return isDict;
    }

    public void setDict(boolean isDict) {
        this.isDict = isDict;
    }

    public boolean isProject() {
        return isProject;
    }

    public void setProject(boolean isProject) {
        this.isProject = isProject;
    }

    public boolean isMetaItem() {
        return isMetaItem;
    }

    public void setMetaItem(boolean isMetaItem) {
        this.isMetaItem = isMetaItem;
    }

    public boolean isMeta() {
        return isMeta;
    }

    public void setMeta(boolean isMeta) {
        this.isMeta = isMeta;
    }

    public boolean isMetaReference() {
        return isMetaReference;
    }

    public void setMetaReference(boolean isMetaReference) {
        this.isMetaReference = isMetaReference;
    }

    public boolean isView() {
        return isView;
    }

    public void setView(boolean isView) {
        this.isView = isView;
    }

    public boolean isDataSource() {
        return isDataSource;
    }

    public void setDataSource(boolean isDataSource) {
        this.isDataSource = isDataSource;
    }
}
