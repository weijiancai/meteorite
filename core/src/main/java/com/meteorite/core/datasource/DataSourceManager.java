package com.meteorite.core.datasource;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBObjectImpl;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.meteorite.core.config.SystemConfig.DIR_SYSTEM_HSQL_DB;
import static com.meteorite.core.config.SystemConfig.SYS_DB_NAME;

/**
 * 数据源管理
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DataSourceManager {
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * 添加数据源
     *
     * @param dataSource 数据源
     */
    public static void addDataSource(DataSource dataSource) throws Exception {
        dataSourceMap.put(dataSource.getName(), dataSource);
    }

    /**
     * 获得数据源
     *
     * @param dataSourceName 数据源名称
     * @return 返回数据源
     */
    public static DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }


    /**
     * 获得所有数据源
     *
     * @since 1.0.0
     * @return 返回所有数据库数据源
     */
    public static List<DataSource> getDataSources() {
        return new ArrayList<>(dataSourceMap.values());
    }

    /**
     * 获得系统数据库sys
     *
     * @return 返回系统数据库sys
     */
    public static DBDataSource getSysDataSource() {
        DBDataSource dataSource = (DBDataSource) dataSourceMap.get(SystemConfig.SYS_DB_NAME);
        if (dataSource == null) {
            File sysDbFile = null;
            try {
                sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYS_DB_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataSource = new DBDataSource(SystemConfig.SYS_DB_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "", SystemConfig.SYS_DB_VERSION);
            dataSource.setDatabaseType(DatabaseType.HSQLDB);
            assert sysDbFile != null;
            dataSource.setFilePath(sysDbFile.getAbsolutePath());
//            dataSourceMap.put(SystemConfig.SYS_DB_NAME, dataSource);
        }
        return dataSource;
    }

    public static INavTreeNode getNavTree() throws Exception {
        List<ITreeNode> children = new ArrayList<>();
        for (DataSource ds : getDataSources()) {
            children.add(ds.getNavTree());
        }
        DBObjectImpl root = new DBObjectImpl("ROOT", "根节点", children);
        root.setObjectType(DBObjectType.NONE);
        return root;
    }
}
