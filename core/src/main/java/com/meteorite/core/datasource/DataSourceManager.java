package com.meteorite.core.datasource;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.classpath.ClassPathDataSource;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBObjectImpl;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import org.apache.log4j.Logger;
import static com.meteorite.core.config.SystemConfig.*;

import java.util.*;

/**
 * 数据源管理
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class DataSourceManager {
    private static final Logger log = Logger.getLogger(DataSourceManager.class);

    private static List<DataSource> dataSources = new ArrayList<DataSource>();
    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    /**
     * 添加数据源
     *
     * @param dataSource 数据源
     */
    public static void addDataSource(DataSource dataSource) throws Exception {
        dataSources.add(dataSource);
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
        return dataSources;
    }

    /**
     * 获得系统数据源
     *
     * @return 返回系统数据源
     */
    public static DBDataSource getSysDataSource() {
        DBDataSource dataSource = (DBDataSource) dataSourceMap.get(SYSTEM_NAME);
        if (dataSource == null) {
           /* File sysDbFile = null;
            try {
                sysDbFile = UFile.createFile(DIR_SYSTEM_HSQL_DB, SYS_DB_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataSource = new DBDataSource(SystemConfig.SYS_DB_NAME, "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/sys", "sa", "", SystemConfig.SYS_DB_VERSION);
            dataSource.setDatabaseType(DatabaseType.HSQLDB);
            assert sysDbFile != null;
            dataSource.setFilePath(sysDbFile.getAbsolutePath());*/
            return initSysDataSource();
        }
        return dataSource;
    }

    /**
     * 从类路径下/db.property文件中初始化系统数据源
     *
     * @return 返回系统数据源
     */
    public static DBDataSource initSysDataSource() {
        // 从类路径下获得db.property配置信息
        ResourceItem dbProperty = ClassPathDataSource.getInstance().getResource("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(dbProperty.getInputStream());
            String driverClass = properties.getProperty("driverClass");
            String url = properties.getProperty("url");
            String userName = properties.getProperty("userName");
            String password = properties.getProperty("password");
            log.debug("初始化系统数据源：" + url);
            DBDataSource dataSource = new DBDataSource(SYSTEM_NAME, driverClass, url, userName, password, SystemConfig.SYS_DB_VERSION);
            dataSourceMap.put(SYSTEM_NAME, dataSource);
            dataSources.add(dataSource);
            return dataSource;
        } catch (Exception e) {
            log.error("获得db.property文件失败！", e);
        }


        return null;
    }

    public static INavTreeNode getNavTree() throws Exception {
        List<ITreeNode> children = new ArrayList<ITreeNode>();
        for (DataSource ds : getDataSources()) {
            if (ds instanceof ClassPathDataSource) {
                continue;
            }
            children.add(ds.getNavTree());
        }
        DBObjectImpl root = new DBObjectImpl("ROOT", "根节点", children);
        root.setObjectType(DBObjectType.NONE);
        return root;
    }

    /**
     * 获得类路径数据源
     *
     * @return 返回类路径数据源
     * @since 1.0.0
     */
    public static ClassPathDataSource getClassPathDataSource() {
        ClassPathDataSource classPathDataSource = (ClassPathDataSource) dataSourceMap.get("classpath");
        if (classPathDataSource == null) {
            classPathDataSource = ClassPathDataSource.getInstance();
            dataSourceMap.put("classpath", classPathDataSource);
        }
        return classPathDataSource;
    }

    /**
     * 获得可用的数据库数据源
     *
     * @return 返回可用的数据库数据源
     * @since 1.0.0
     */
    public static List<DBDataSource> getAvailableDbDataSource() {
        List<DBDataSource> result = new ArrayList<DBDataSource>();
        for (DataSource ds : dataSourceMap.values()) {
            if (ds instanceof DBDataSource && ds.isAvailable()) {
                result.add((DBDataSource) ds);
            }
        }
        return result;
    }
}
