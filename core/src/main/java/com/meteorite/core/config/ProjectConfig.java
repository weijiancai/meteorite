package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.util.UtilFile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
@XmlType (propOrder = {"projectName", "userHome", "userDir", "dataSources"})
public class ProjectConfig {
    public static final String DIR_NAME_SQLDB = "hsqldb"; // hsqldb数据库存储数据库文件的目录名
    public static final String DIR_NAME_DBCONF = "dbconf"; // 数据库配置信息目录名

    private static String userHome = System.getProperty("user.home");
    private static String userDir = System.getProperty("user.dir");
    private static String projectName;

    private List<DataSource> dataSources = new ArrayList<DataSource>();

    public ProjectConfig() {}

    public ProjectConfig(String projectName) {
        this.projectName = projectName;
    }

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @XmlElementWrapper(name = "DataSources")
    @XmlElement(name = "DataSource")
    public List<DataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSource> dataSources) {
        this.dataSources = dataSources;
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
