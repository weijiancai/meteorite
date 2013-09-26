package com.meteorite.core.db;

import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"driverClass", "url", "username", "password", "dbVersion", "filePath"})
@MetaElement(displayName = "数据源")
public class DataSource {
    private String name;
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private String filePath; // 数据库文件路径
    private String dbVersion; // 数据库版本

    public DataSource() {}

    public DataSource(String name, String driverClass, String url, String username, String password, String dbVersion) {
        this.name = name;
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
        this.dbVersion = dbVersion;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "驱动类")
    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @MetaFieldElement(displayName = "数据库URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @MetaFieldElement(displayName = "用户名")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @MetaFieldElement(displayName = "密码")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @MetaFieldElement(displayName = "数据库文件路径")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @MetaFieldElement(displayName = "数据库版本")
    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }
}
