package com.meteorite.core.config;

import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 系统配置信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class SystemConfig {
    // ================================= 系统信息 ==============================================
    public static final String SYSTEM_NAME = "metaui"; // 系统名称
    public static SystemType SYSTEM_TYPE = SystemType.DESKTOP; // 系统类型，默认桌面系统

    public static final String DIR_NAME_SQLDB = "hsqldb"; // hsqldb数据库存储数据库文件的目录名
    public static final String DIR_NAME_DBCONF = "dbconf"; // 数据库配置信息目录名

    public static final String FILE_NAME_PROJECT_CONFIG = "ProjectConfig.xml"; // 项目配置文件名
    public static final String FILE_NAME_SYSTEM_INFO = "SystemInfo.xml"; // 系统信息文件名
    /**
     * 布局配置文件名
     */
    public static final String FILE_NAME_LAYOUT_CONFIG = "/LayoutConfig.xml";
    /**
     * 元数据字段配置文件名
     */
    public static final String FILE_NAME_META_FIELD_CONFIG = "MetaFieldConfig.xml";
    /**
     * 数据字典配置文件名
     */
    public static final String FILE_NAME_DICT_CATEGORY = "DictCategory.xml";

    /**
     * 系统默认目录
     */
    public static File DIR_SYSTEM;
    /**
     * 类路径目录
     */
    public static File DIR_CLASS_PATH = UFile.getClassPathDir();
    /**
     * 系统默认数据库目录
     */
    public static File DIR_SYSTEM_HSQL_DB;
    /**
     * 数据库升级脚本目录
     */
    public static File DIR_DB_UPGRADE = UFile.makeDirs(DIR_CLASS_PATH, "dbversion");

    // ================================= 数据库信息 ==============================================
    /**
     * 系统数据库版本
     */
    public static final String SYS_DB_VERSION = "1.0.0";
    /**
     * 系统数据库版本表名
     */
    public static final String SYS_DB_VERSION_TABLE_NAME = "mu_db_version";

    /**
     * 是否是开发模式
     */
    public static final boolean isDebug = true;
}
