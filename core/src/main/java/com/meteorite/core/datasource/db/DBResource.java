package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.impl.DBObjectList;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBResource extends VirtualResource {
    private DataSource dataSource;
    private DBObject dbObject;
    private ResourceType type;
    private DBResource parent;

    public DBResource(DataSource dataSource, DBObject dbObject) {
        this.dataSource = dataSource;
        this.dbObject = dbObject;
        if (dbObject != null) {
            type = new DefaultResourceType(dbObject.getObjectType().name(), "数据库" + dbObject.getObjectType().name(), dbObject.getIcon());
            if (dbObject.getParent() != null) {
                parent = new DBResource(dataSource, (DBObject) dbObject.getParent());
            }
        }
    }

    @Override
    public String getName() {
        return dbObject.getName();
    }

    @Override
    public String getDisplayName() {
        return dbObject.getComment();
    }

    @Override
    public String getPath() {
        DBObjectType dbType = dbObject.getObjectType();
        if (DBObjectType.SCHEMA == dbType) {
            return "schema/" + getName();
        } else if (DBObjectType.TABLE == dbType) {
            return "table/" + getName();
        }
        return dbObject.getFullName();
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public ResourceType getResourceType() {
        return type;
    }

    @Override
    public MetaDataType getDataType() {
        if (dbObject instanceof DBColumn) {
            return ((DBColumn) dbObject).getDataType();
        }
        return null;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public View getView() {
        return dbObject.getView();
    }

    @Override
    public VirtualResource getParent() {
        return parent;
    }

    @Override
    public List<VirtualResource> getChildren() throws Exception {
        List<VirtualResource> children = new ArrayList<VirtualResource>();
        DBObjectType dbType = dbObject.getObjectType();
        if (DBObjectType.SCHEMA == dbType) {
            DBSchema schema = (DBSchema) dbObject;
            // 加载表
            DBObjectList tables = new DBObjectList("Tables", DBIcons.DBO_TABLES, new ArrayList<ITreeNode>());
            tables.setSchema(schema);
            // 加载视图
            DBObjectList views = new DBObjectList("Views", DBIcons.DBO_VIEWS, new ArrayList<ITreeNode>());
            views.setSchema(schema);
            // 加载索引
            DBObjectList indexes = new DBObjectList("Indexes", DBIcons.DBO_INDEXES, new ArrayList<ITreeNode>());
            indexes.setSchema(schema);
            // 加载触发器
            DBObjectList triggers = new DBObjectList("Triggers", DBIcons.DBO_TRIGGERS, new ArrayList<ITreeNode>());
            triggers.setSchema(schema);
            // 加载存储过程
            DBObjectList procedures = new DBObjectList("Procedures", DBIcons.DBO_PROCEDURES, new ArrayList<ITreeNode>());
            procedures.setSchema(schema);
            // 加载函数
            DBObjectList functions = new DBObjectList("Functions", DBIcons.DBO_FUNCTIONS, new ArrayList<ITreeNode>());
            functions.setSchema(schema);

            children.add(new DBResource(dataSource, tables));
            children.add(new DBResource(dataSource, views));
            children.add(new DBResource(dataSource, indexes));
            children.add(new DBResource(dataSource, triggers));
            children.add(new DBResource(dataSource, procedures));
            children.add(new DBResource(dataSource, functions));
        } else if (DBObjectType.TABLE == dbType) {
            // 列
            DBObjectList columns = new DBObjectList("Columns", DBIcons.DBO_COLUMNS, new ArrayList<ITreeNode>());
            columns.setParent(dbObject);
            // 约束
            DBObjectList constraints = new DBObjectList("Constraints", DBIcons.DBO_CONSTRAINTS, new ArrayList<ITreeNode>());
            constraints.setParent(dbObject);

            children.add(new DBResource(dataSource, columns));
            children.add(new DBResource(dataSource, constraints));
        } else if (DBIcons.DBO_TABLES.equals(dbObject.getIcon())) {
            List<DBTable> tables = dbObject.getSchema().getTables();
            for (DBTable table : tables) {
                children.add(new DBResource(dataSource, table));
            }
        } else if (DBIcons.DBO_VIEWS.equals(dbObject.getIcon())) {
            List<DBView> views = dbObject.getSchema().getViews();
            for (DBView view : views) {
                children.add(new DBResource(dataSource, view));
            }
        } else if (DBIcons.DBO_INDEXES.equals(dbObject.getIcon())) {
            List<DBIndex> indexes = dbObject.getSchema().getIndexes();
            for (DBIndex index : indexes) {
                children.add(new DBResource(dataSource, index));
            }
        } else if (DBIcons.DBO_TRIGGERS.equals(dbObject.getIcon())) {
            List<DBTrigger> triggers = dbObject.getSchema().getTriggers();
            for (DBTrigger trigger : triggers) {
                children.add(new DBResource(dataSource, trigger));
            }
        } else if (DBIcons.DBO_PROCEDURES.equals(dbObject.getIcon())) {
            List<DBProcedure> procedures = dbObject.getSchema().getProcedures();
            for (DBProcedure procedure : procedures) {
                children.add(new DBResource(dataSource, procedure));
            }
        } else if (DBIcons.DBO_FUNCTIONS.equals(dbObject.getIcon())) {
            List<DBFunction> functions = dbObject.getSchema().getFunctions();
            for (DBFunction function : functions) {
                children.add(new DBResource(dataSource, function));
            }
        } else if (DBIcons.DBO_COLUMNS.equals(dbObject.getIcon())) {
            DBTable table = (DBTable) dbObject.getParent();
            List<DBColumn> columns = table.getColumns();
            for (DBColumn column : columns) {
                children.add(new DBResource(dataSource, column));
            }
        } else if (DBIcons.DBO_CONSTRAINTS.equals(dbObject.getIcon())) {
            DBTable table = (DBTable) dbObject.getParent();
            List<DBConstraint> constraints = table.getConstraints();
            for (DBConstraint constraint : constraints) {
                children.add(new DBResource(dataSource, constraint));
            }
        }

        return children;
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Object getOriginalObject() {
        return dbObject;
    }

    // ===================================== 数据内容处理 =====================================================

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws Exception {
        QueryBuilder builder = QueryBuilder.create(meta);
        for (ICanQuery query : queryList) {
            for (ICanQuery.Condition condition : query.getConditions()) {
                builder.add(condition.colName, condition.queryModel, condition.value, condition.dataType, true);
            }
        }
        builder.sql().setQuerySql(meta.getSqlText());

        return JdbcTemplate.queryForResult(builder, (DBDataSource) this.getDataSource(), page, rows);
    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder builder, int page, int rows) throws Exception {
        DBDataSource dataSource = (DBDataSource) this.getDataSource();
        builder.sql().from(getName());
//        builder.sql().from(table.getFullName());
        /*for (MetaReference ref : meta.getReferences()) {
            String fkTableName = ref.getFkMeta().getDbTable().getName();
            String fkField = ref.getFkMetaField().getColumn().getName();
            String pkTableName = ref.getPkMeta().getDbTable().getName();
            String pkField = ref.getPkMetaField().getColumn().getName();
            String join = String.format("%s on %s=%s", pkTableName, fkTableName + "." + fkField, pkTableName + "." + pkField);
            builder.sql().join(join);
        }*/
        return JdbcTemplate.queryForResult(builder, dataSource, page, rows);
    }

    @Override
    public void save(final Map<String, IValue> valueMap) throws Exception {
        IPDB pdb = new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                for (IValue value : valueMap.values()) {
                    MetaField field = value.getMetaField();
                    String originalName = field.getOriginalName();
                    String tableName = originalName.substring(0, originalName.indexOf("."));
                    Map<String, Object> param;
                    if (map.containsKey(tableName)) {
                        param = map.get(tableName);
                    } else {
                        param = new HashMap<String, Object>();
                        map.put(tableName, param);
                    }
                    param.put(originalName, value.value());
                }

                return map;
            }
        };

        JdbcTemplate.save((DBDataSource) this.getDataSource(), pdb);
    }

    @Override
    public void save(IPDB pdb) throws Exception {
        JdbcTemplate.save((DBDataSource) this.getDataSource(), pdb);
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {
        List<String> pkColumns = new ArrayList<String>();
        String tableName = null;
        for (MetaField field : meta.getFields()) {
            if (field.isPk()) {
                tableName = field.getOriginalName().split("\\.")[0];
                pkColumns.add(field.getOriginalName());
            }
        }
        if (tableName == null || pkColumns.size() == 0 || keys == null || keys.length == 0) {
            return;
        }

        JdbcTemplate template = new JdbcTemplate((DBDataSource) this.getDataSource());
        Map<String, Object> params = new HashMap<String, Object>();

        for (int i = 0; i < pkColumns.size(); i++) {
            params.put(pkColumns.get(i), keys[i]);
        }
        try {
            template.delete(params, tableName);
            template.commit();
        } finally {
            template.close();
        }
    }

    @Override
    public void update(Map<String, Object> valueMap, Map<String, Object> conditionMap, String tableName) throws Exception {
        JdbcTemplate template = new JdbcTemplate((DBDataSource) this.getDataSource());
        try {
            template.update(valueMap, conditionMap, tableName);
            template.commit();
        } finally {
            template.close();
        }
    }
}
