package com.meteorite.core.config;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UtilFile;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType (propOrder = {"name", "displayName", "dataSources"})
@MetaElement(displayName = "项目配置", sortNum = 0)
public class ProjectConfig {
    private String name;
    private String displayName;

    private List<DBDataSource> dataSources = new ArrayList<DBDataSource>();

    public ProjectConfig() {}

    public ProjectConfig(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "项目名称", sortNum = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "项目中文名", sortNum = 20)
    public String getDisplayName() {
        return UString.isEmpty(displayName, name);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlElementWrapper(name = "DataSources")
    @XmlElement(name = "DBDataSource")
    @MetaFieldElement(displayName = "数据源", sortNum = 40, dataType = MetaDataType.DATA_SOURCE)
    public List<DBDataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DBDataSource> dataSources) {
        this.dataSources = dataSources;
    }

// ===================== 静态方法 ==================================

    public File getProjectDir() {
        return UtilFile.makeDirs(SystemConfig.DIR_SYSTEM, name);
    }

    public File getHsqldbDir() {
        return UtilFile.makeDirs(getProjectDir(), SystemConfig.DIR_NAME_SQLDB);
    }

    public File getDbConfDir() {
        return UtilFile.makeDirs(getProjectDir(), SystemConfig.DIR_NAME_DBCONF);
    }

    public File getProjectConfigFile() {
        return new File(getProjectDir(), SystemConfig.FILE_NAME_PROJECT_CONFIG);
    }
}
