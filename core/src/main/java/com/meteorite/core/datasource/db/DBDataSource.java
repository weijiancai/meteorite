package com.meteorite.core.datasource.db;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;
import com.meteorite.core.datasource.db.object.impl.DBObjectImpl;
import com.meteorite.core.datasource.db.sql.SqlBuilder;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.exception.NotFoundResourceException;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.datasource.request.*;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.rest.PathHandler;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.OutputStream;
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
@XmlType(propOrder = {"databaseType", "driverClass", "url", "userName", "pwd", "dbVersion", "filePath"})
@MetaElement(displayName = "数据库数据源")
public class DBDataSource extends DataSource {
    private static final Logger log = Logger.getLogger(DBDataSource.class);

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
    private boolean isAvailable;

    private VirtualResource rootResource;

    public DBDataSource() {
        initProperties();
    }

    public DBDataSource(String name, String driverClass, String url, String username, String password, String dbVersion) {
        this.name = name;

        initProperties();
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(username);
        setPwd(password);
        setDbVersion(dbVersion);
    }

    private void initProperties() {
        List<MetaField> fields = new ArrayList<MetaField>();
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
    @MetaFieldElement(displayName = "名称", sortNum = 10)
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @MetaFieldElement(displayName = "用户名", sortNum = 20)
    @XmlAttribute
    public String getUserName() {
        return properties.getFieldValue(USER_NAME);
    }

    public void setUserName(String username) {
        properties.setFieldValue(USER_NAME, username);
    }

    @MetaFieldElement(displayName = "密码", dataType = MetaDataType.PASSWORD, sortNum = 30)
    @XmlAttribute
    public String getPwd() {
        return properties.getFieldValue(PASSWORD);
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "数据库类型", dataType = MetaDataType.DICT, dictId = "DatabaseType", sortNum = 40)
    public DatabaseType getDatabaseType() throws Exception {
        String type = properties.getFieldValue(DATABASE_TYPE);
        if (UString.isNotEmpty(type)) {
            return DatabaseType.get(properties.getFieldValue(DATABASE_TYPE));
        }
        DatabaseType dbType = getDbConnection().getDatabaseType();
        setDatabaseType(dbType);
        return dbType;
    }

    @MetaFieldElement(displayName = "驱动类", sortNum = 50)
    public String getDriverClass() {
        return properties.getFieldValue(DRIVER_CLASS);
    }

    @MetaFieldElement(displayName = "数据库URL", sortNum = 60)
    @XmlAttribute
    public String getUrl() {
        return properties.getFieldValue(URL);
    }

    @MetaFieldElement(displayName = "数据库文件路径", sortNum = 70)
    public String getFilePath() {
        return properties.getFieldValue(FILE_PATH);
    }

    @MetaFieldElement(displayName = "数据库版本", sortNum = 80)
    public String getDbVersion() {
        return properties.getFieldValue(DB_VERSION);
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.DATABASE;
    }

    @Override
    public VirtualResource getRootResource() throws Exception {
        if (rootResource == null) {
            rootResource = new DBResource(this, getDbConnection().getSchema());
        }
        return rootResource;
    }

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws Exception {
        QueryBuilder builder = QueryBuilder.create(meta);
        for (ICanQuery query : queryList) {
            for (ICanQuery.Condition condition : query.getConditions()) {
                builder.add(condition.colName, condition.queryModel, condition.value, condition.dataType, true);
            }
        }

        return retrieve(builder, page, rows);
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {
        /*DBDataset table = meta.getDbTable();
        List<DBColumn> pkColumns = table.getPkColumns();
        if (pkColumns.size() == 0 || keys == null || keys.length == 0) {
            return;
        }

        JdbcTemplate template = new JdbcTemplate(this);
        Map<String, Object> params = new HashMap<String, Object>();

        for (int i = 0; i < pkColumns.size(); i++) {
            params.put(pkColumns.get(i).getName(), keys[i]);
        }
        try {
            template.delete(params, table.getName());
            template.commit();
        } finally {
            template.close();
        }*/
    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    @JSONField(serialize = false)
    public ITreeNode getNavTree() throws Exception {
        return navTree;
    }

    @Override
    public ITreeNode getNavTree(String parent) throws Exception {
        return null;
    }

    @Override
    public List<ITreeNode> getChildren(String parent) throws Exception {
        return null;
    }

    @Override
    public void load() throws Exception {
        if (navTree == null) {
            navTree = new DBObjectImpl();
            navTree.setObjectType(DBObjectType.DATABASE);
            navTree.setName(name);
        }
        getDbConnection().getLoader().load();
    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder builder, int page, int rows) throws Exception {
        /*Meta meta = builder.getMeta();
        DBDataset table = builder.getMeta().getDbTable();
        if (table.getDataSource().getDatabaseType() == DatabaseType.SQLSERVER) {
            builder.sql().from(table.getSchema().getName() + ".dbo." + table.getName());
        } else {
            builder.sql().from(table.getSchema().getName() + "." + table.getName());
        }
//        builder.sql().from(table.getFullName());
        for (MetaReference ref : meta.getReferences()) {
            String fkTableName = ref.getFkMeta().getDbTable().getName();
            String fkField = ref.getFkMetaField().getColumn().getName();
            String pkTableName = ref.getPkMeta().getDbTable().getName();
            String pkField = ref.getPkMetaField().getColumn().getName();
            String join = String.format("%s on %s=%s", pkTableName, fkTableName + "." + fkField, pkTableName + "." + pkField);
            builder.sql().join(join);
        }*/

        QueryResult<DataMap> queryResult = new QueryResult<DataMap>();
        queryResult.setPageRows(rows);

        builder.sql().build(getDatabaseType());
        JdbcTemplate template = new JdbcTemplate(this);
        List<DataMap> list = new ArrayList<DataMap>();
        try {
            // 查询rows
            list = template.queryForList(builder, page, rows);
            // 查询total rows
            if (page >= 0) {
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

    @Override
    public void save(final Map<String, IValue> valueMap) throws Exception {
        IPDB pdb = new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                /*for (IValue value : valueMap.values()) {
                    MetaField field = value.getMetaField();
                    String tableName = field.getColumn().getDataset().getName();
                    Map<String, Object> param;
                    if (map.containsKey(tableName)) {
                        param = map.get(tableName);
                    } else {
                        param = new HashMap<String, Object>();
                        map.put(tableName, param);
                    }
                    param.put(field.getColumn().getName(), value.value());
                }*/

                return map;
            }
        };

        save(pdb);
    }

    @Override
    public void save(IPDB pdb) throws Exception {
        JdbcTemplate template = new JdbcTemplate(this);
        try {
            template.save(pdb);
            template.commit();
        } finally {
            template.close();
        }
    }


    @Override
    @JSONField(serialize = false)
    public boolean isAvailable() {
        try {
            return getDbConnection().isAvailable();
        } catch (Exception e) {
            log.error("获得数据库连接失败！", e);
        }
        return false;
    }

    @Override
    public void write(String id, OutputStream os) throws Exception {

    }

    @Override
    public VirtualResource findResourceByPath(String path) {
        try {
            String tableStart = "/table/";
            if (path.startsWith(tableStart)) {
                DBObject table = getDbConnection().getLoader().getTable(path.substring(tableStart.length()));
                if (table != null) {
                    return new DBResource(this, table);
                }
            }
        } catch (Exception e) {
            throw new NotFoundResourceException(path);
        }

        return null;
    }

    @Override
    public List<VirtualResource> findResourcesByPath(String path) {
        List<VirtualResource> list = new ArrayList<VirtualResource>();

        try {
            if ("/tables".equals(path)) {
                DBConnection conn = getDbConnection();
                List<DBTable> tables = conn.getLoader().loadTables(conn.getSchema());
                for (DBTable table : tables) {
                    list.add(new DBResource(this, table));
                }
            } else if ("/views".equals(path)) {
                DBConnection conn = getDbConnection();
                List<DBView> views = conn.getLoader().loadViews(conn.getSchema());
                for (DBView view : views) {
                    list.add(new DBResource(this, view));
                }
            } else if (path.endsWith("/columns")) {
                PathHandler handler = new PathHandler(path);
                Map<String, String> map = handler.parseForDb();
                String tableName = map.get("table");
                DBTable table = getDbConnection().getLoader().getTable(tableName);
                DBConnection conn = getDbConnection();
                List<DBColumn> columns = conn.getLoader().loadColumns(table);
                for (DBColumn column : columns) {
                    list.add(new DBResource(this, column));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundResourceException(path);
        }

        return list;
    }

    @Override
    public ResourceItem getResource(String path) {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDbVersion(String dbVersion) {
        properties.setFieldValue(DB_VERSION, dbVersion);
    }

    @JSONField(name = "children", serialize = false)
    public List<DBSchema> getSchemas() throws Exception {
        return getDbConnection().getSchemas();
    }

    public void setDatabaseType(DatabaseType databaseType) {
        properties.setFieldValue(DATABASE_TYPE, databaseType.getName());
    }

    public void setDriverClass(String driverClass) {
        properties.setFieldValue(DRIVER_CLASS, driverClass);
    }

    public void setUrl(String url) {
        properties.setFieldValue(URL, url);
    }

    public void setPwd(String password) {
        properties.setFieldValue(PASSWORD, password);
    }

    public void setFilePath(String filePath) {
        properties.setFieldValue(FILE_PATH, filePath);
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
        if(this.findResourceByPath("/table/" + SystemConfig.SYS_DB_VERSION_TABLE_NAME) != null) {
            List<DataMap> result = conn.getResultSet(sql);
            if (result.size() > 0) {
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
    @JSONField(serialize = false)
    public DBConnection getDbConnection() throws Exception {
        if (connection == null) {
            connection = new DBConnectionImpl(this);
        }
        return connection;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public VirtualResource get(IRequest request) {
        return null;
    }

    @Override
    public void post(IRequest request) {

    }

    @Override
    public void put(IRequest request) throws Exception {
        Map<String, String> keyMap = request.getPathHandler().parseForDb();
        Map<String, String> params = request.getParams();
        if (keyMap.containsKey("column")) { // 修改列
            String tableName = keyMap.get("table");
            String columnName = keyMap.get("column");
            if (params.containsKey("nullable")) { // 设置列属性是否可为空
                boolean nullable = UString.toBoolean(params.get("nullable"));
                getDbConnection().getLoader().updateColumnNullable(tableName, columnName, nullable);
            }
        } else if(keyMap.containsKey("table")) { // 修改表

        }
    }

    @Override
    public void delete(IRequest request) {

    }

    @Override
    public IResponse exp(IRequest request) throws Exception {
        if(request instanceof ExpDbDdlRequest) {
            ExpDbDdlRequest ddlReq = (ExpDbDdlRequest) request;

            return expDdl(ddlReq);
        }
        Map<String, String> map = request.getPathHandler().parseForDb();
        String tableName = map.get("table");
        JdbcTemplate template = new JdbcTemplate(this);
        QueryBuilder builder = QueryBuilder.create(tableName, request.getConditions());
        builder.sql().build(getDatabaseType());
        List<DataMap> listData = template.queryForList(builder, -1, 0);
        BaseResponse response = (BaseResponse) request.getResponse();
        response.setListData(listData);
        return response;
    }

    @Override
    public void imp(IRequest request) throws Exception {
        ExpDataRequest expRequest = (ExpDataRequest) request;

        Map<String, String> map = request.getPathHandler().parseForDb();
        Map<String, String> colMapping = expRequest.getColMapping();
        String tableName = map.get("table");
        JdbcTemplate template = new JdbcTemplate(this);
        IResponse response = request.getResponse();
        List<DataMap> listData = response.getListData();
        for (DataMap dataMap : listData) {
            String[] excludeColumns = expRequest.getExcludeColumns();
            if (excludeColumns != null) {
                for (String column : excludeColumns) {
                    dataMap.remove(column);
                }
            }
            for (Map.Entry<String, Object> entry : expRequest.getDefaultValues().entrySet()) {
                String key = colMapping.get(entry.getKey());
                if (key == null) {
                    dataMap.put(entry.getKey(), entry.getValue());
                } else {
                    dataMap.put(key, entry.getValue());
                }
            }
            template.save(dataMap, tableName);
        }
        template.commit();
    }

    public IResponse expDdl(ExpDbDdlRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        StringBuilder dropTableStr = new StringBuilder();
        StringBuilder createTableStr = new StringBuilder();
        StringBuilder constraintStr = new StringBuilder();
        StringBuilder createIndexStr = new StringBuilder();
        StringBuilder dropIndexStr = new StringBuilder();

        DBSchema schema = getDbConnection().getSchema();
        // 导出表
        List<DBTable> tables = schema.getTables();
        for (DBTable table : tables) {
            createTableStr.append("/*==============================================================*/\r\n");
            createTableStr.append(String.format("/* Table: %s                                      */\r\n", table.getName()));
            createTableStr.append("/*==============================================================*/\r\n");

            StringBuilder commentStr = new StringBuilder();
            StringBuilder pkStr = new StringBuilder();
            switch (request.getExpDbType()) {
                case HSQLDB: {
                    dropTableStr.append(String.format("drop table if exists %s;\r\n", table.getName()));
                    createTableStr.append(String.format("create table %s\r\n(\r\n", table.getName()));
                    if (UString.isNotEmpty(table.getDisplayName())) {
                        commentStr.append(String.format("comment on table %s is '%s';\r\n", table.getName(), table.getDisplayName()));
                    }
                    for (DBColumn column : table.getColumns()) {
                        createTableStr.append(String.format("    %-32s %-15s %s,\r\n", column.getName(), column.getDbDataType(DatabaseType.HSQLDB), column.isNullable() ? "" : "not null"));
                        if (UString.isNotEmpty(column.getDisplayName())) {
                            commentStr.append(String.format("comment on column %s.%s is '%s';\r\n", table.getName(), column.getName(), column.getDisplayName()));
                        }
                        if (column.isPk()) {
                            pkStr.append(column.getName()).append(",");
                        }
                    }
                    if (pkStr.length() > 0) {
                        if (pkStr.charAt(pkStr.length() - 1) == ',') {
                            pkStr.deleteCharAt(pkStr.length() - 1);
                        }
                        createTableStr.append(String.format("    primary key (%s)\r\n", pkStr));
                    }
                    createTableStr.append(");\r\n").append(commentStr).append("\r\n");
                }
            }
        }

        // 外键约束
        List<DBConstraint> constraints = schema.getFkConstraints();
        for (DBConstraint constraint : constraints) {
            constraintStr.append(String.format("alter table %s add constraint %s foreign key (%s)\r\n", constraint.getFkTableName(), constraint.getName(), constraint.getFkColumnName()));
            constraintStr.append(String.format("    references %s (%s) on delete cascade on update cascade;\r\n\r\n", constraint.getPkTableName(), constraint.getPkColumnName()));
        }

        // 索引
        List<DBIndex> indexes = schema.getIndexes();
        for (DBIndex index : indexes) {
            String indexName = index.getName();
            if (indexName.equals("PRIMARY") || indexName.startsWith("FK_")) {
                continue;
            }
            createIndexStr.append(String.format("create %s index %s on %s\r\n(\r\n", index.isUnique() ? "unique" : "", index.getName(), index.getTableName()));
            for (int i = 0; i < index.getColumnNames().size(); i++) {
                String columnName = index.getColumnNames().get(i);
                createIndexStr.append("    ").append(columnName);
                if (i < index.getColumnNames().size() - 1) {
                    createIndexStr.append(",");
                }
                createIndexStr.append("\r\n");
            }
            createIndexStr.append(");\r\n");
            dropIndexStr.append(String.format("drop index %s if exists;\r\n", index.getName()));
        }

        /*System.out.println(dropTableStr);
        System.out.println(dropIndexStr);
        System.out.println(createTableStr);
        System.out.println(constraintStr);
        System.out.println(createIndexStr);*/
        String resultSql = String.format("%s\r\n%s\r\n%s\r\n%s\r\n%s", dropTableStr, dropIndexStr, createTableStr, constraintStr, createIndexStr);
        String savePath = request.getSaveFilePath();
        if (UString.isNotEmpty(savePath)) {
            UFile.write(resultSql, savePath);
        }

        return response;
    }
}
