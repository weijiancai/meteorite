package com.meteorite.core.config;

import com.meteorite.core.util.UtilFile;

import java.io.File;

/**
 * 系统配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class SystemConfig {
    public static final String DIR_NAME_METEORITE = ".meteorite"; // 系统名称
    public static final String DIR_NAME_SQLDB = "hsqldb"; // hsqldb数据库存储数据库文件的目录名
    public static final String DIR_NAME_DBCONF = "dbconf"; // 数据库配置信息目录名

    public static final String FILE_NAME_PROJECT_CONFIG = "ProjectConfig.xml"; // 项目配置文件名
    /**
     * 布局配置文件名
     */
    public static final String FILE_NAME_LAYOUT_CONFIG = "/LayoutConfig.xml";

    public static String userHome = System.getProperty("user.home");
    public static String userDir = System.getProperty("user.dir");

    /**
     * 系统默认目录
     */
    public static File DIR_SYSTEM = UtilFile.makeDirs(userHome, DIR_NAME_METEORITE);
    /**
     * 系统默认数据库目录
     */
    public static File DIR_SYSTEM_HSQL_DB = UtilFile.makeDirs(DIR_SYSTEM, DIR_NAME_SQLDB);

    /**
     * 系统数据库版本
     */
    public static final String SYS_DB_VERSION = "1.0.0";
    /**
     * 系统数据库名称
     */
    public static final String SYS_DB_NAME = "sys";
    /**
     * 系统数据库版本表名
     */
    public static final String SYS_DB_VERSION_TABLE_NAME = "sys_db_version";
}
