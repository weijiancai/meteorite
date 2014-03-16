package com.meteorite.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 系统信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SysInfo {
    private static Properties infoProp = new Properties();
//    private static final File USER_HOME = new File(SystemConfig.userHome);
//    private static final File DIR_FLY_SYS = new File(USER_HOME, ".flysys");
    private static final File FILE_SYS_INFO = new File(SystemConfig.DIR_SYSTEM, "/info.xml");

    private static boolean isDbmsInit = false;
    private static boolean isClassDefInit = false;
    private static boolean isProjectDefInit = false;
    private static boolean isModuleDefInit = false;
    private static boolean isDictInit = false;

    public static void store() {
        try {
            infoProp.storeToXML(new FileOutputStream(FILE_SYS_INFO), "Sys Info", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDbmsInit() {
        return isDbmsInit;
    }

    public static void setDbmsInit(boolean dbmsInit) {
        isDbmsInit = dbmsInit;
        infoProp.setProperty("isDbmsInit", dbmsInit + "");
    }

    public static boolean isClassDefInit() {
        return isClassDefInit;
    }

    public static void setClassDefInit(boolean classDefInit) {
        isClassDefInit = classDefInit;
        infoProp.setProperty("isClassDefInit", classDefInit + "");
    }

    public static boolean isProjectDefInit() {
        return isProjectDefInit;
    }

    public static void setProjectDefInit(boolean projectDefInit) {
        isProjectDefInit = projectDefInit;
        infoProp.setProperty("isProjectDefInit", projectDefInit + "");
    }

    public static boolean isModuleDefInit() {
        return isModuleDefInit;
    }

    public static void setModuleDefInit(boolean moduleDefInit) {
        isModuleDefInit = moduleDefInit;
        infoProp.setProperty("isModuleDefInit", moduleDefInit + "");
    }

    public static boolean isDictInit() {
        return isDictInit;
    }

    public static void setDictInit(boolean dictInit) {
        isDictInit = dictInit;
        infoProp.setProperty("isDictInit", dictInit + "");
    }

    static {
        // 从类路径中装载系统信息
        try {
            if (!FILE_SYS_INFO.exists()) {
                FILE_SYS_INFO.createNewFile();
                infoProp.setProperty("isDbmsInit", "false");
                infoProp.setProperty("isClassDefInit", "false");
                infoProp.setProperty("isProjectDefInit", "false");
                infoProp.setProperty("isModuleDefInit", "false");
                infoProp.setProperty("isDictInit", "false");
                store();
            } else {
                infoProp.loadFromXML(new FileInputStream(FILE_SYS_INFO));

                String sIsDbmsInit = infoProp.getProperty("isDbmsInit");
                isDbmsInit = "true".equalsIgnoreCase(sIsDbmsInit);
                String sIsClassDefInit = infoProp.getProperty("isClassDefInit");
                isClassDefInit = "true".equalsIgnoreCase(sIsClassDefInit);
                isProjectDefInit = "true".equalsIgnoreCase(infoProp.getProperty("isProjectDefInit"));
                isModuleDefInit = "true".equalsIgnoreCase(infoProp.getProperty("isModuleDefInit"));
                isDictInit = "true".equalsIgnoreCase(infoProp.getProperty("isDictInit"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
