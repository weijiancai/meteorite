package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.ui.layout.model.Layout;
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
@XmlType (propOrder = {"projectName", "projectCnName", "userHome", "userDir", "dataSources", "layout"})
@MetaElement(cname = "项目配置", sortNum = 0)
public class ProjectConfig {
    public static final String DIR_NAME_SQLDB = "hsqldb"; // hsqldb数据库存储数据库文件的目录名
    public static final String DIR_NAME_DBCONF = "dbconf"; // 数据库配置信息目录名

    private static String userHome = System.getProperty("user.home");
    private static String userDir = System.getProperty("user.dir");
    private static String projectName;
    private static String projectCnName;

    private List<DataSource> dataSources = new ArrayList<DataSource>();
    private Layout layout;

    public ProjectConfig() {}

    public ProjectConfig(String projectName) {
        this.projectName = projectName;
    }

    @MetaFieldElement(displayName = "用户主目录", sortNum = 30)
    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    @MetaFieldElement(displayName = "项目名称", sortNum = 10)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @MetaFieldElement(displayName = "项目中文名", sortNum = 20)
    public String getProjectCnName() {
        return projectCnName;
    }

    public void setProjectCnName(String projectCnName) {
        ProjectConfig.projectCnName = projectCnName;
    }

    @XmlElementWrapper(name = "DataSources")
    @XmlElement(name = "DataSource")
    @MetaFieldElement(displayName = "数据源", sortNum = 40)
    public List<DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    @XmlElement(name = "Layout")
    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    // ===================== 静态方法 ==================================

    public static File getProjectDir() {
        return UtilFile.makeDirs(userHome, projectName);
    }

    public static File getHsqldbDir() {
        return UtilFile.makeDirs(userHome, DIR_NAME_SQLDB);
    }

    public static File getDbConfDir() {
        return UtilFile.makeDirs(userHome, DIR_NAME_DBCONF);
    }

    public static File getProjectConfigFile() {
        return new File(getProjectDir(), "ProjectConfig.xml");
    }
}
