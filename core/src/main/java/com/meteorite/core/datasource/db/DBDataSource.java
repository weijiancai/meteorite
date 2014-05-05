package com.meteorite.core.datasource.db;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;
import com.meteorite.core.datasource.db.object.impl.DBObjectImpl;
import com.meteorite.core.datasource.db.object.impl.DBObjectList;
import com.meteorite.core.datasource.db.object.DBDataset;
import com.meteorite.core.datasource.db.sql.SqlBuilder;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"databaseType", "driverClass", "url", "username", "password", "dbVersion", "filePath"})
@MetaElement(displayName = "数据库数据源")
public class DBDataSource implements DataSource {
    public static final String DRIVER_CLASS = "driverClass";
    public static final String URL = "url";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String FILE_PATH = "filePath";
    public static final String DB_VERSION = "dbVersion";
    public static final String DATABASE_TYPE = "databaseType";
    public static final String HOST = "host";
    public static final String PORT = "port";

    private String name;

    private Meta properties;
    private DBConnection connection;
    private DBObjectImpl navTree;

    public DBDataSource() {
        initProperties();
    }

    public DBDataSource(String name, String driverClass, String url, String username, String password, String dbVersion) {
        this.name = name;

        initProperties();
        setDriverClass(driverClass);
        setUrl(url);
        setUsername(username);
        setPassword(password);
        setDbVersion(dbVersion);
    }

    private void initProperties() {
        List<MetaField> fields = new ArrayList<>();
        fields.add(new MetaField(DRIVER_CLASS, "驱动类"));
        fields.add(new MetaField(URL, "数据库URL"));
        fields.add(new MetaField(USER_NAME, "用户名"));
        fields.add(new MetaField(PASSWORD, "密码"));
        fields.add(new MetaField(FILE_PATH, "数据库文件路径"));
        fields.add(new MetaField(DB_VERSION, "数据库版本"));
        fields.add(new MetaField(DATABASE_TYPE, "数据库类型", DictManager.getDict(DatabaseType.class).getId()));

        properties = new Meta("DBDataSourceProperties", "数据库连接属性");
        properties.setFields(fields);
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "数据库类型", dataType = MetaDataType.DICT, dictId = "DatabaseType")
    public DatabaseType getDatabaseType() {
        return DatabaseType.get(properties.getFieldValue(DATABASE_TYPE));
    }

    public void setDatabaseType(DatabaseType databaseType) {
        properties.setFieldValue(DATABASE_TYPE, databaseType.getName());
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称")
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.DATABASE;
    }

    @Override
    @JSONField(serialize = false)
    public Meta getProperties() {
        return properties;
    }

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws SQLException {
        QueryBuilder builder = QueryBuilder.create(meta);
        for (ICanQuery query : queryList) {
            for (ICanQuery.Condition condition : query.getConditions()) {
                builder.add(condition.colName, condition.queryModel, condition.value, condition.dataType);
            }
        }

        return retrieve(builder, page, rows);
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {
        DBDataset table = meta.getDbTable();
        List<DBColumn> pkColumns = table.getPkColumns();
        if (pkColumns.size() == 0 || keys == null || keys.length == 0) {
            return;
        }

        JdbcTemplate template = new JdbcTemplate(this);
        Map<String, Object> params = new HashMap<>();

        for (int i = 0; i < pkColumns.size(); i++) {
            params.put(pkColumns.get(i).getName(), keys[i]);
        }
        try {
            template.delete(params, table.getName());
            template.commit();
        } finally {
            template.close();
        }
    }

    @Override
    public INavTreeNode getNavTree() throws Exception {
        if (navTree == null) {
            List<DBSchema> schemas = getSchemas();
            DBObjectList dbSchemas = new DBObjectList("Schemas", DBIcons.DBO_SCHEMAS, new ArrayList<ITreeNode>(schemas));

            DBLoader loader = connection.getLoader();

            List<DBUser> users = loader.loadUsers();
            DBObjectList dbUsers = new DBObjectList("Users", DBIcons.DBO_USERS, new ArrayList<ITreeNode>(users));

            List<DBObject> privileges = loader.loadPrivileges();
            DBObjectList dbPrivileges = new DBObjectList("Privileges", DBIcons.DBO_PRIVILEGES, new ArrayList<ITreeNode>(privileges));

            List<DBObject> charsetList = loader.loadCharsets();
            DBObjectList dbCharsets = new DBObjectList("Charset", null, new ArrayList<ITreeNode>(charsetList));

            List<ITreeNode> list = new ArrayList<>();
            list.add(dbSchemas);
            list.add(dbUsers);
            list.add(dbPrivileges);
            list.add(dbCharsets);
            navTree = new DBObjectImpl(name, name, list);
            navTree.setObjectType(DBObjectType.DATABASE);
        }

        return navTree;
    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder builder, int page, int rows) throws SQLException {
        DBDataset table = builder.getMeta().getDbTable();
        builder.sql().from(table.getSchema().getName() + "." + table.getName());

        QueryResult<DataMap> queryResult = new QueryResult<>();
        queryResult.setPageRows(rows);

        builder.sql().build(getDatabaseType());
        JdbcTemplate template = new JdbcTemplate(this);
        List<DataMap> list = new ArrayList<>();
        try {
            // 查询rows
            list = template.queryForList(builder, page, rows);
            // 查询total rows
            if (page > 0) {
                queryResult.setTotal(template.queryForInt(builder.sql().getCountSql(), builder.sql().getParamsValue()));
            } else {
                queryResult.setTotal(list.size());
                queryResult.setPageRows(list.size());
            }
        } finally {
            template.close();
        }

        queryResult.setRows(list);

        return queryResult;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "驱动类")
    public String getDriverClass() {
        return properties.getFieldValue(DRIVER_CLASS);
    }

    public void setDriverClass(String driverClass) {
        properties.setFieldValue(DRIVER_CLASS, driverClass);
    }

    @MetaFieldElement(displayName = "数据库URL")
    public String getUrl() {
        return properties.getFieldValue(URL);
    }

    public void setUrl(String url) {
        properties.setFieldValue(URL, url);
    }

    @MetaFieldElement(displayName = "用户名")
    public String getUsername() {
        return properties.getFieldValue(USER_NAME);
    }

    public void setUsername(String username) {
        properties.setFieldValue(USER_NAME, username);
    }

    @MetaFieldElement(displayName = "密码", dataType = MetaDataType.PASSWORD)
    public String getPassword() {
        return properties.getFieldValue(PASSWORD);
    }

    public void setPassword(String password) {
        properties.setFieldValue(PASSWORD, password);
    }

    @MetaFieldElement(displayName = "数据库文件路径")
    public String getFilePath() {
        return properties.getFieldValue(FILE_PATH);
    }

    public void setFilePath(String filePath) {
        properties.setFieldValue(FILE_PATH, filePath);
    }

    @MetaFieldElement(displayName = "数据库版本")
    public String getDbVersion() {
        return properties.getFieldValue(DB_VERSION);
    }

    public void setDbVersion(String dbVersion) {
        properties.setFieldValue(DB_VERSION, dbVersion);
    }

    @JSONField(name = "children")
    public List<DBSchema> getSchemas() throws Exception {
        return getDbConnection().getSchemas();
    }

    /**
     * 获得某个系统的最大版本号
     *
     * @param systemName 系统名称
     * @return 返回某个系统的最大版本号
     */
    public String getMaxVersion(String systemName) throws Exception {
        String sql = SqlBuilder.create()
                .from(SystemConfig.SYS_DB_VERSION_TABLE_NAME)
                .max("db_version")
                .where(String.format("sys_name='%s'", systemName))
                .group("sys_name")
                .build(getDatabaseType());

        DBConnection conn = getDbConnection();
        if (DBUtil.existsTable(conn, SystemConfig.SYS_DB_VERSION_TABLE_NAME)) {
            List<DataMap> result = conn.getResultSet(sql);
            if(result.size() > 0) {
                return result.get(0).getString("max_db_version");
            }
        }

        return "0.0.0";
    }

    /**
     * 获得数据库连接信息
     *
     * @return 返回数据库连接信息
     */
    public DBConnection getDbConnection() throws Exception {
        if (connection == null) {
            connection = new DBConnectionImpl(this);
        }
        return connection;
    }
}
