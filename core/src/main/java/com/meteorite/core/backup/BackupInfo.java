package com.meteorite.core.backup;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.project.ProjectDefine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 备份信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
public class BackupInfo {
    private List<DictCategory> dictList;
    private List<ProjectDefine> projectList;
    private List<Meta> metaList;

    @XmlElementWrapper(name = "DictCategories")
    @XmlElement(name = "Category")
    public List<DictCategory> getDictList() {
        return dictList;
    }

    public void setDictList(List<DictCategory> dictList) {
        this.dictList = dictList;
    }

    @XmlElementWrapper(name = "Projects")
    @XmlElement(name = "Project")
    public List<ProjectDefine> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDefine> projectList) {
        this.projectList = projectList;
    }

    @XmlElementWrapper(name = "Metas")
    @XmlElement(name = "Meta")
    public List<Meta> getMetaList() {
        return metaList;
    }

    public void setMetaList(List<Meta> metaList) {
        this.metaList = metaList;
    }
}
